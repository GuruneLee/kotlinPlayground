package com.gurunelee;

import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://blog.naver.com/pcmola/222064258040"> Java Garbage Collector(자바 가비지 수집기) </a>
 */
public class HeapDumpTutorial {
    private Map<Integer, MemoryObject> leak = new HashMap<>();

    public static void main(String[] args) {
        HeapDumpTutorial heapDumpTutorial = new HeapDumpTutorial();
        heapDumpTutorial.run();
    }

    public void run() {
        for (int i = 0; ; i++) {
            // 해시맵에 저장만 하고 빼내지 않아 결국 OOM이 나는 구조.
            leak.put(i, new MemoryObject(i));
            System.out.println("leaking object " + leak.get(i).index);
            if (i == 5000) {
                try {
                    // 데모를 위해 5000번 이후 sleep.
                    System.out.println("Sleeping after adding " + i + "th element.");
//                    Thread.sleep(100000000L);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MemoryObject {
        int index;

        MemoryObject(final int index) {
            this.index = index;
        }
    }
}
