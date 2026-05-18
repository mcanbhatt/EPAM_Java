package com.epam.practice.java8;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;

// Simulated native memory holder
class NativeResource {
    private final long address;

    public NativeResource(long address) {
        this.address = address;
    }

    public long getAddress() {
        return address;
    }
}

// Cleaner using PhantomReference
class NativeCleaner {

    private static final ReferenceQueue<NativeResource> queue = new ReferenceQueue<>();
    private static final Set<ResourceRef> refs = new HashSet<>();

    static {
        Thread cleanerThread = new Thread(() -> {
            while (true) {
                try {
                    ResourceRef ref = (ResourceRef) queue.remove(); // blocks
                    ref.cleanup();  // free native memory
                    refs.remove(ref);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    public static void register(NativeResource resource) {
        ResourceRef ref = new ResourceRef(resource, queue);
        refs.add(ref); // keep PhantomReference alive
    }

    // PhantomReference subclass
    static class ResourceRef extends PhantomReference<NativeResource> {
        private final long address;

        public ResourceRef(NativeResource referent, ReferenceQueue<? super NativeResource> q) {
            super(referent, q);
            this.address = referent.getAddress(); // store metadata BEFORE GC
        }

        public void cleanup() {
            System.out.println("Cleaning native memory at address: " + address);
            freeMemory(address);
        }

        private void freeMemory(long address) {
            // Simulate freeing native memory
            // In real case: Unsafe.freeMemory(address)
        }
    }
}

// Demo
public class PhantomReferenceDemo {
    public static void main(String[] args) throws Exception {

        NativeResource resource = new NativeResource(12345L);

        NativeCleaner.register(resource);

        // Remove strong reference
        resource = null;

        System.gc();

        Thread.sleep(2000); // give GC time
    }
}