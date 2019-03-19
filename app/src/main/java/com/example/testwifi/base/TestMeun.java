package com.example.testwifi.base;

public enum TestMeun {
    ADD {
        public int addData(int a, int b) {
            return a + b;
        }
    },
    DEL {
        public boolean isDelSuccess(int position) {
            return position % 2 == 0;
        }
    },
    UPDATA {
        public boolean isUpdataSuccess(int a) {
            return a / 2 == 0;
        }
    },
    REQUEST {
        public int requestCode(int a) {
            return a;
        }
    }
}
