package cn.shulaoh.sort;

public class SortFunctions {

    public static void main(String[] args) {
        int[] array = {8, 23, 42, 5, 6, 78, 33, 9, 2};
        quickSort(array, 0, array.length - 1);
        System.out.println(array);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot=partition(arr, low, high);
            quickSort(arr, low, pivot-1);
            quickSort(arr, pivot+1, high);
        }
    }

    private static int partition(int[] arr, int low, int high){
        int pivot = arr[low];     //枢轴记录
        while (low<high){
            while (low<high && arr[high]>=pivot) --high;
            arr[low]=arr[high];             //交换比枢轴小的记录到左端
            while (low<high && arr[low]<=pivot) ++low;
            arr[high] = arr[low];           //交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        //返回的是枢轴的位置
        return low;
    }
}