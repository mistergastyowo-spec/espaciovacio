package com.espaciovacio.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

/** Cielo del Espacio Vacio: negro total, sin sol, luna ni nubes, solo estrellas. */
public class VoidSpaceEffects extends DimensionSpecialEffects {
    private static final int STAR_COUNT = 6000;

    private VertexBuffer starBuffer;

    public VoidSpaceEffects() {
        super(Float.NaN, false, SkyType.NONE, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
        return fogColor;
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack,
                             Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        if (starBuffer == null) {
            buildStars();
        }
        setupFog.run();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        // Giro muy lento para que el cielo no se sienta congelado.
        poseStack.mulPose(Axis.XP.rotationDegrees((ticks + partialTick) * 0.004F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(23.0F));

        FogRenderer.setupNoFog();
        starBuffer.bind();
        starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
        VertexBuffer.unbind();

        poseStack.popPose();
        setupFog.run();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        return true;
    }

    /** Misma tecnica que las estrellas de vanilla, pero con mas estrellas y algo mas grandes. */
    private void buildStars() {
        if (starBuffer != null) {
            starBuffer.close();
        }
        starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);

        RandomSource random = RandomSource.create(42069L);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (int i = 0; i < STAR_COUNT; ++i) {
            double x = random.nextFloat() * 2.0F - 1.0F;
            double y = random.nextFloat() * 2.0F - 1.0F;
            double z = random.nextFloat() * 2.0F - 1.0F;
            double size = 0.12D + random.nextFloat() * 0.18D;
            double lengthSq = x * x + y * y + z * z;
            if (lengthSq < 1.0D && lengthSq > 0.01D) {
                lengthSq = 1.0D / Math.sqrt(lengthSq);
                x *= lengthSq;
                y *= lengthSq;
                z *= lengthSq;
                double px = x * 100.0D;
                double py = y * 100.0D;
                double pz = z * 100.0D;
                double yaw = Math.atan2(x, z);
                double sinYaw = Math.sin(yaw);
                double cosYaw = Math.cos(yaw);
                double pitch = Math.atan2(Math.sqrt(x * x + z * z), y);
                double sinPitch = Math.sin(pitch);
                double cosPitch = Math.cos(pitch);
                double roll = random.nextDouble() * Math.PI * 2.0D;
                double sinRoll = Math.sin(roll);
                double cosRoll = Math.cos(roll);

                for (int j = 0; j < 4; ++j) {
                    double cx = ((j & 2) - 1) * size;
                    double cy = ((j + 1 & 2) - 1) * size;
                    double rx = cx * cosRoll - cy * sinRoll;
                    double ry = cy * cosRoll + cx * sinRoll;
                    double dy = rx * sinPitch;
                    double dz = -rx * cosPitch;
                    double dx = dz * sinYaw - ry * cosYaw;
                    double dzz = ry * sinYaw + dz * cosYaw;
                    builder.vertex(px + dx, py + dy, pz + dzz).endVertex();
                }
            }
        }

        BufferBuilder.RenderedBuffer rendered = builder.end();
        starBuffer.bind();
        starBuffer.upload(rendered);
        VertexBuffer.unbind();
    }
}
