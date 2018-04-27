package com.yrw.sort;

import java.util.Arrays;

/**
 * 堆排序，由大到小，利用最小堆
 *
 * @author yrw
 * @since 2018/4/20
 */
public class HeapSort {

  public void heapSort(int[] heap) {
    if (heap == null || heap.length == 0) {
      return;
    }
    buildHeap(heap);
    for (int i = heap.length - 1; i >= 0; i--) {
      swap(heap, 0, i);
      percolateDown(heap, 0, i - 1);
    }
  }

  public void swap(int[] nums, int a, int b) {
    if (nums[a] != nums[b]) {
      nums[a] ^= nums[b];
      nums[b] ^= nums[a];
      nums[a] ^= nums[b];
    }
  }

  public void buildHeap(int[] heap) {
    for (int i = heap.length / 2; i >= 0; i--) {
      percolateDown(heap, i, heap.length - 1);
    }
  }

  public void percolateDown(int[] heap, int hole, int maxSize) {
    int child;
    int tmp = heap[hole];
    for (; hole * 2 <= maxSize; hole = child) {
      child = hole * 2;
      if (child != maxSize && heap[child + 1] < heap[child]) {
        child++;
      }
      if (heap[child] < tmp) {
        heap[hole] = heap[child];
      } else {
        break;
      }
    }
    heap[hole] = tmp;
  }

  public static void main(String[] args) {
    HeapSort heapSort = new HeapSort();
    int[] array = {1, 3, 99, 6, 7, 8, 9};
    heapSort.heapSort(array);
    System.out.println(Arrays.toString(array));
  }
}
