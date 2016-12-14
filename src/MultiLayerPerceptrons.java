/**
 * Created by YongJang on 2016-12-05.
 */
public class MultiLayerPerceptrons {
    public static void main(String args[]) {
        int N = 2;          // 두 개의 입력 뉴런
        int H = 2;          // 두 개의 은닉 뉴런
        int M = 1;          // 한 개의 출력 뉴런
        double eta = 0.2;   // 학습률의 설정

        double E = 100;        // 아래 while문 안으로 진입하도록 비용함수를 큰 값으로 초기화
        double Es = 0.01
                ;   // 프로그램을 정지시킬 때 기준이 되는 비용 함수
        int count = 0;      // 반복학습 횟수 계수기의 초기화

        // I-2 단계
        // 연결 가중치와 임계치를 -0.5 ~ 0.5 의 임의 실수값으로 설정
        double wJi[][] = new double[2][3];
        double wKj[][] = new double[1][3];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                wJi[i][j] = (Math.random()) - 0.5;
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                wKj[i][j] = (Math.random()) - 0.5;
            }
        }

        double netPj[] = new double[H];
        double oPj[] = new double[H + 1];
        double netPk[] = new double[M];
        double oPk[] = new double[M];
        double eP[] = new double[4];
        double deltaPk[] = new double[M];
        double deltaPj[] = new double[H];

        while (E > Es) {
            int j = 0;

            count = count + 1;
            E = 0;          // 비용 함수를 0으로 초기화
            int[][] X = {{0, 0, -1}, {0, 1, -1}, {1, 0, -1}, {1, 1, -1}};   // 입력값
            int[] D = {0, 1, 1, 0};                                         // 목표값

            for (int p = 0; p < 4; p++) {
                for (j = 0; j < H; j++) {
                    netPj[j] = 0;
                    for (int i = 0; i < N + 1; i++) {
                        netPj[j] = netPj[j] + wJi[j][i] * X[p][i];
                    }
                    oPj[j] = 1 / (1 + Math.exp(-netPj[j]));
                }
                oPj[H] = -1;        // 출력 노드의 임계치를 배우기 위한 설정

                for (int k = 0; k < M; k++) {
                    netPk[k] = 0;
                    for (j = 0; j < H + 1; j++) {
                        netPk[k] = netPk[k] + wKj[k][j] * oPj[j];
                    }
                    oPk[k] = 1 / (1 + Math.exp(-netPk[k]));
                }
                eP[p] = 0;

                for (int k = 0; k < M; k++) {
                    deltaPk[k] = (D[p] - oPk[k]) * oPk[k] * (1 - oPk[k]);
                    eP[p] = eP[p] + 0.5 * Math.pow((D[p] - oPk[k]), 2);
                }

                E = E + eP[p];

                for (j = 0; j < H; j++) {
                    deltaPj[j] = 0;
                    for (int k = 0; k < M; k++) {
                        deltaPj[j] = deltaPj[j] + deltaPk[k] * wKj[k][j] * oPj[j] * (1 - oPj[j]);
                    }
                }

                for (int k = 0; k < M; k++) {
                    for (j = 0; j < H + 1; j++) {
                        wKj[k][j] = wKj[k][j] + eta * deltaPk[k] * oPj[j];
                    }
                }

                for (j = 0; j < H; j++) {
                    for (int i = 0; i < N + 1; i++) {
                        wJi[j][i] = wJi[j][i] + eta * deltaPj[j] * X[p][i];
                    }
                }
            }
            System.out.println("Count : " + count + " / 오차의 변화 추이 : " + E);
        }
        System.out.println();
        System.out.println("XOR 연산을 올바르게 학습하게된 퍼셉트론의 가중치");
        System.out.println("==========w_ij 출력(은닉층과 입력층간의 연결 가중치)==========");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("w" + i + "" + j + " = " + wJi[i][j] + " / ");
            }
        }

        System.out.println();
        System.out.println("==========w_jk 출력(은닉층과 출력층간의 연결 가중치)==========");

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("w" + i + "" + j + " = " + wKj[i][j] + " / ");
            }
        }
        System.out.println();
    }
}
