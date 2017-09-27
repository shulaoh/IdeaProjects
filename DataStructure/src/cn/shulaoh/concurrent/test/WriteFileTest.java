package cn.shulaoh.concurrent.test;

import java.io.File;

public class WriteFileTest {

    public static class FileWriterWorker implements Runnable {

        public String name;

        public String content;

        public File file;

        public FileWriterWorker(String name, String content) {
            this.name = name;
            this.content = content;
        }

        @Override
        public void run() {

        }
    }

    public static void main(String[] args) {
        File file1 = new File("/Users/Shulaoh/IdeaProjects/DataStructure/A.txt");
        File file2 = new File("/Users/Shulaoh/IdeaProjects/DataStructure/B.txt");
        File file3 = new File("/Users/Shulaoh/IdeaProjects/DataStructure/C.txt");
        File file4 = new File("/Users/Shulaoh/IdeaProjects/DataStructure/D.txt");


    }
}
