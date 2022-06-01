package terramine.common.utility;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class Utilities {
    private Utilities() {}

    public static BlockHitResult rayTraceBlocks(Entity entity, double length, boolean checkLiquids)
    {
        if (checkLiquids) {
            return entity.level.clip(new ClipContext(new Vec3(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z()),
                    entity.getLookAngle().scale(length).add(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z()), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity));
        } else {
            return entity.level.clip(new ClipContext(new Vec3(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z()),
                    entity.getLookAngle().scale(length).add(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        }
    }

    public static EntityHitResult rayTraceEntity(Entity entity, Entity self, double length)
    {
        Vec3 vec3 = new Vec3(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z());
        Vec3 vec33 = entity.getLookAngle().scale(length).add(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z());
        Predicate<Entity> predicate = new Predicate<Entity>() {
            @Override
            public boolean test(Entity entity) {
                return !(entity instanceof Player || entity == self || entity instanceof ItemEntity || entity instanceof ExperienceOrb);
            }
        };
        return ProjectileUtil.getEntityHitResult(entity.level, entity, vec3, vec33, (new AABB(vec3, vec33)).inflate(1.0D), predicate);
    }


    /**
     * Rotates the given entity towards its motion vector at the given speed.
     */
    public static void rotateTowardsMotion(Entity entity, float speed)
    {
        float horizontalMotion = (float) Math.sqrt(entity.getDeltaMovement().x() * entity.getDeltaMovement().x() + entity.getDeltaMovement().z() * entity.getDeltaMovement().z());
        entity.setXRot((float) (Math.atan2(entity.getDeltaMovement().x(), entity.getDeltaMovement().z()) * (180D / Math.PI)));
        for (entity.setYRot((float) (Math.atan2(entity.getDeltaMovement().y(), horizontalMotion) * (180D / Math.PI))); entity.getYRot() - entity.yRotO < -180.0F; entity.yRotO -= 360.0F) {
            while (entity.getYRot() - entity.yRotO >= 180F) entity.yRotO += 360F;
            while (entity.getXRot() - entity.xRotO < -180F) entity.xRotO -= 360F;
            while (entity.getXRot() - entity.xRotO >= 180F) entity.xRotO += 360F;
            entity.setYRot(entity.yRotO + (entity.getYRot() - entity.yRotO) * speed);
            entity.setXRot(entity.xRotO + (entity.getXRot() - entity.xRotO) * speed);
        }
    }

    /**
     * Returns a random unit vector inside a sphere cap defined by a given axis vector and angle.
     * @see <a href="https://math.stackexchange.com/questions/56784/generate-a-random-direction-within-a-cone">source</a>
     */
    public static Vec3 sampleSphereCap(Vec3 coneAxis, float angle)
    {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Choose an axis
        Vec3 axis;
        if(Math.abs(coneAxis.x) < Math.abs(coneAxis.y))
        {
            if(Math.abs(coneAxis.x) < Math.abs(coneAxis.z)) axis = new Vec3(1D, 0D, 0D);
            else axis = new Vec3(0D, 0D, 1D);
        }
        else
        {
            if(Math.abs(coneAxis.y) < Math.abs(coneAxis.z)) axis = new Vec3(0D, 1D, 0D);
            else axis = new Vec3(0D, 0D, 1D);
        }

        // Construct two mutually orthogonal vectors, both of which are orthogonal to the cone's axis vector
        Vec3 u = coneAxis.cross(axis);
        Vec3 v = coneAxis.cross(u);

        // Uniformly  sample an angle of rotation around the cone's axis vector
        float phi = random.nextFloat() * 2F * (float) Math.PI;

        // Uniformly sample an angle of rotation around one of the orthogonal vectors
        float theta = (float) Math.acos(random.nextDouble((double) Math.cos(angle), 1D));

        // Construct a unit vector uniformly distributed on the spherical cap
        return  coneAxis.scale(Math.cos(theta)).add(u.scale(Math.cos(phi) * Math.sin(theta))).add(v.scale(Math.sin(phi) * Math.sin(theta)));
    }
}
