package edu.uclm.esi.juegos.services;

import org.springframework.stereotype.Service;
import java.util.Arrays;

import com.google.common.hash.Hashing;


@Service
public class Connect4Service {

	public boolean checkWinner(String board[][], int lastP[]) {

		int adjacencyUp[] = { -1, 0 };
		int adjacencyDown[] = { 1, 0 };
		int adjacencyLeft[] = { 0, -1 };
		int adjacencyRight[] = { 0, 1 };
		int adjacencyUpRight[] = { -1, 1 };
		int adjacencyDownLeft[] = { 1, -1 };
		int adjacencyUpLeft[] = { -1, -1 };
		int adjacencyDownRight[] = { 1, 1 };

		if (checkHorizontalMoves(board, lastP, adjacencyLeft, adjacencyRight, 0, 0, 0)
				|| checkVerticalMoves(board, lastP, adjacencyUp, adjacencyDown, 0, 0, 0)
				|| checkDiagonalMoves(board, lastP, adjacencyUpRight, adjacencyUpLeft, adjacencyDownRight,
						adjacencyDownLeft, 0, 0, 0, 0, 0)) {
			return true;
		}

		return false;
	}

	public boolean checkTie(String board[][], int total_moves) {

		if (total_moves == 42) {
			return true;
		}

		return false;
	}

	private boolean checkHorizontalMoves(String board[][], int lastPosition[], int adjacencyL[], int adjacencyR[],
			int cWinner, int limitL, int limitR) {

		// ¿TENOG QUE HACER COPIAS DE LOS ARRAYS PQ ES PASO POR REFERENCIA?

		if (cWinner >= 3) {
			return true;
		}

		// lastPosition = [3,2]

		// int limitRowUp = lastPosition[0];
		// int limitRowDown = 6 - lastPosition[0];
		int limitColumnL = lastPosition[1];
		int limitColumnR = 5 - lastPosition[1];
		// COMPROBACION A IZQUIERDAS
		if (board[adjacencyL[0] + lastPosition[0]][adjacencyL[1] + lastPosition[1]]
				.equals(board[lastPosition[0]][lastPosition[1]]) && limitL < limitColumnL) {
			adjacencyL[1]--;
			checkHorizontalMoves(board, lastPosition, adjacencyL, adjacencyR, ++cWinner, ++limitL, limitR);
		}

		// COMPROBACION A DERECHAS
		if (board[adjacencyR[0] + lastPosition[0]][adjacencyR[1] + lastPosition[1]]
				.equals(board[lastPosition[0]][lastPosition[1]]) && limitR < limitColumnR) {
			adjacencyR[1]++;
			checkHorizontalMoves(board, lastPosition, adjacencyL, adjacencyR, ++cWinner, limitL, ++limitR);
		}

		return false;

	}

	private boolean checkVerticalMoves(String board[][], int lastPosition[], int adjacencyU[], int adjacencyD[],
			int cWinner, int limitU, int limitD) {

		if (cWinner >= 3) {
			return true;
		}

		int limitRowUp = lastPosition[0];
		int limitRowDown = 6 - lastPosition[0];

		// Comprobacion HACIA ARRIBA

		if (board[adjacencyU[0] + lastPosition[0]][adjacencyU[1] + lastPosition[1]]
				.equals(board[lastPosition[0]][lastPosition[1]]) && limitU < limitRowUp) {
			adjacencyU[0]--;
			checkVerticalMoves(board, lastPosition, adjacencyU, adjacencyD, ++cWinner, ++limitU, limitD);
		}

		// Comprobacion HACIA ABAJO
		if (board[adjacencyD[0] + lastPosition[0]][adjacencyD[1] + lastPosition[1]]
				.equals(board[lastPosition[0]][lastPosition[1]]) && limitD < limitRowDown) {
			adjacencyD[0]++;
			checkVerticalMoves(board, lastPosition, adjacencyU, adjacencyD, ++cWinner, limitU, ++limitD);
		}

		return false;
	}

	private boolean checkDiagonalMoves(String board[][], int lastPosition[], int adjacencyUR[], int adjacencyUL[],
			int adjacencyDR[], int adjacencyDL[], int cWinner, int limitUR, int limitUL, int limitDR, int limitDL) {

		if (cWinner >= 3) {
			return true;
		}

		int limitUpRight = 0;
		int limitUpLeft = 0;
		int limitDownRight = 0;
		int limitDownLeft = 0;
		int cornerA[] = { 0, 0 };
		int cornerB[] = { 0, 5 };
		int cornerC[] = { 6, 0 };
		int cornerD[] = { 6, 5 };
		int cornerE[] = { 1, 1 };
		int cornerF[] = { 1, 4 };
		int cornerG[] = { 5, 1 };
		int cornerH[] = { 5, 4 };

		// PRIMER CASO ESPECIAL ESQUINAS(X1)
		if (Arrays.equals(lastPosition, cornerA) || Arrays.equals(cornerB, lastPosition)
				|| Arrays.equals(cornerC, lastPosition) || Arrays.equals(cornerD, lastPosition)) {

			limitDownRight = 5;
			limitDownLeft = 5;
			limitUpRight = 5;
			limitUpLeft = 5;

			if (Arrays.equals(cornerA, lastPosition)) {

				// LIMIT DOWNRIGHT
				if (board[adjacencyDR[0] + lastPosition[0]][adjacencyDR[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]]) && limitDR < limitDownRight) {

					adjacencyDR[0]++;
					adjacencyDR[1]++;

					checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
							++cWinner, limitUR, limitUL, ++limitDR, limitDL);

				}

			} else if (Arrays.equals(cornerB, lastPosition)) {

				if (board[adjacencyDL[0] + lastPosition[0]][adjacencyDL[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]]) && limitDL < limitDownLeft) {

					adjacencyDL[0]++;
					adjacencyDL[1]++;

					checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
							++cWinner, limitUR, limitUL, limitDR, ++limitDL);

				}

			} else if (Arrays.equals(cornerC, lastPosition)) {

				if (board[adjacencyUR[0] + lastPosition[0]][adjacencyUR[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]]) && limitUR < limitUpRight) {

					adjacencyUR[0]--;
					adjacencyUR[1]++;

					checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
							++cWinner, ++limitUR, limitUL, limitDR, limitDL);

				}

			} else {

				if (board[adjacencyUL[0] + lastPosition[0]][adjacencyUL[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]]) && limitUL < limitUpLeft) {

					adjacencyUL[0]--;
					adjacencyUL[1]--;

					checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
							++cWinner, limitUR, ++limitUL, limitDR, limitDL);

				}

			}

			// SEGUNDO CASO ESPECIAL LATERALES
		} else if (lastPosition[0] == 0 || lastPosition[0] == 6 || lastPosition[1] == 0 || lastPosition[1] == 5) {

			if (lastPosition[0] == 0) {

				if (lastPosition[1] >= 3) {
					limitDownLeft = lastPosition[1];

					if (board[adjacencyDL[0] + lastPosition[0]][adjacencyDL[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitDL < limitDownLeft) {

						adjacencyDL[0]++;
						adjacencyDL[1]--;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, limitUL, limitDR, ++limitDL);

					}

				} else {
					limitDownRight -= lastPosition[1];
					if (board[adjacencyDR[0] + lastPosition[0]][adjacencyDR[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitDR < limitDownRight) {

						adjacencyDR[0]++;
						adjacencyDR[1]++;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, limitUL, ++limitDR, limitDL);
					}

				}

			} else if (lastPosition[1] == 0) {

				if (lastPosition[0] > 3) {
					limitUpRight = lastPosition[0];

					if (board[adjacencyUR[0] + lastPosition[0]][adjacencyUR[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitUR < limitUpRight) {

						adjacencyUR[0]--;
						adjacencyUR[1]++;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, ++limitUR, limitUL, limitDR, limitDL);

					}
				} else if (lastPosition[0] < 3) {
					limitDownRight = 3; // siempre???

					if (board[adjacencyDR[0] + lastPosition[0]][adjacencyDR[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitDR < limitDownRight) {

						adjacencyDR[0]++;
						adjacencyDR[1]++;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, limitUL, ++limitDR, limitDL);
					}

				} else {// AQUI ES IGUAL A 3
					limitDownRight = 3;
					limitUpRight = 3;

					if (board[adjacencyDR[0] + lastPosition[0]][adjacencyDR[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitDR < limitDownRight) {

						adjacencyDR[0]++;
						adjacencyDR[1]++;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, limitUL, ++limitDR, limitDL);
					}

					if (board[adjacencyUR[0] + lastPosition[0]][adjacencyUR[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitUR < limitUpRight) {

						adjacencyUR[0]--;
						adjacencyUR[1]++;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, ++limitUR, limitUL, limitDR, limitDL);

					}

				}

			} else if (lastPosition[0] == 6) {

				limitUpLeft = 3;
				limitUpRight = 3;

				if (lastPosition[1] < 3) {

					if (board[adjacencyUR[0] + lastPosition[0]][adjacencyUR[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitUR < limitUpRight) {

						adjacencyUR[0]--;
						adjacencyUR[1]++;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, ++limitUR, limitUL, limitDR, limitDL);

					}
				} else if (lastPosition[1] > 3) {

					if (board[adjacencyUL[0] + lastPosition[0]][adjacencyUL[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitUL < limitUpLeft) {

						adjacencyUL[0]--;
						adjacencyUL[1]--;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, ++limitUL, limitDR, limitDL);
					}
				}

			} else {

				limitDownLeft = 3;
				limitUpLeft = 3;

				if (lastPosition[0] > 3) {

					if (board[adjacencyUL[0] + lastPosition[0]][adjacencyUL[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitUL < limitUpLeft) {

						adjacencyUL[0]--;
						adjacencyUL[1]--;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, ++limitUL, limitDR, limitDL);
					}

				} else if (lastPosition[0] < 3) {

					if (board[adjacencyDL[0] + lastPosition[0]][adjacencyDL[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitDL < limitDownLeft) {

						adjacencyDL[0]++;
						adjacencyDL[1]--;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, limitUL, limitDR, ++limitDL);

					}

				} else {

					if (board[adjacencyDL[0] + lastPosition[0]][adjacencyDL[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitDL < limitDownLeft) {

						adjacencyDL[0]++;
						adjacencyDL[1]--;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, limitUL, limitDR, ++limitDL);

					}

					if (board[adjacencyUL[0] + lastPosition[0]][adjacencyUL[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitUL < limitUpLeft) {

						adjacencyUL[0]--;
						adjacencyUL[1]--;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, ++limitUL, limitDR, limitDL);
					}

				}

			}

			// ESTO SERAN LAS SEGUNDAS ESQUINA(X2)
		} else if (Arrays.equals(cornerE, lastPosition) || Arrays.equals(cornerF, lastPosition)
				|| Arrays.equals(cornerG, lastPosition) || Arrays.equals(cornerH, lastPosition)) {

			// PRIMERA, SEGUNDA ESQUINA
			if (Arrays.equals(cornerE, lastPosition)) {

				if (board[adjacencyUL[0] + lastPosition[0]][adjacencyUL[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]])) {

					limitDownRight = 2;

					if (board[adjacencyDR[0] + lastPosition[0]][adjacencyDR[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitDR < limitDownRight) {

						adjacencyDR[0]++;
						adjacencyDR[1]++;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, limitUL, ++limitDR, limitDL);

					}
				}

				if (board[adjacencyDR[0] + lastPosition[0]][adjacencyDR[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]]) && limitDR < limitDownRight) {

					limitDownRight = 3;

					adjacencyDR[0]++;
					adjacencyDR[1]++;

					checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
							++cWinner, limitUR, limitUL, ++limitDR, limitDL);

				}

				// SEGUNDA, SEGUNDA ESQUINA
			} else if (Arrays.equals(cornerF, lastPosition)) {

				if (board[adjacencyUR[0] + lastPosition[0]][adjacencyUR[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]])) {

					limitDownLeft = 2;

					if (board[adjacencyDL[0] + lastPosition[0]][adjacencyDL[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitDL < limitDownLeft) {

						adjacencyDL[0]++;
						adjacencyDL[1]--;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, limitUL, limitDR, ++limitDL);

					}

				}

				if (board[adjacencyDL[0] + lastPosition[0]][adjacencyDL[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]]) && limitDL < limitDownLeft) {

					limitDownLeft = 3;

				}

			} else if (Arrays.equals(cornerG, lastPosition)) {
				
				if(board[adjacencyDL[0] + lastPosition[0]][adjacencyDL[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]])) {
					
					limitUpRight = 2;
					
					if (board[adjacencyUR[0] + lastPosition[0]][adjacencyUR[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitUR < limitUpRight) {

						adjacencyUR[0]--;
						adjacencyUR[1]++;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, ++limitUR, limitUL, limitDR, limitDL);

					}
					
					
				}
				
				if (board[adjacencyUR[0] + lastPosition[0]][adjacencyUR[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]]) && limitUR < limitUpRight) {

					adjacencyUR[0]--;
					adjacencyUR[1]++;

					checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
							++cWinner, ++limitUR, limitUL, limitDR, limitDL);

				}
				
				
			} else {
				
				if(board[adjacencyDR[0] + lastPosition[0]][adjacencyDR[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]])) {
					
					limitUpLeft = 2;
					
					if (board[adjacencyUL[0] + lastPosition[0]][adjacencyUL[1] + lastPosition[1]]
							.equals(board[lastPosition[0]][lastPosition[1]]) && limitUL < limitUpLeft) {

						adjacencyUL[0]--;
						adjacencyUL[1]--;

						checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
								++cWinner, limitUR, ++limitUL, limitDR, limitDL);
					}
					
					
				}
				
				if (board[adjacencyUL[0] + lastPosition[0]][adjacencyUL[1] + lastPosition[1]]
						.equals(board[lastPosition[0]][lastPosition[1]]) && limitUL < limitUpLeft) {

					adjacencyUL[0]--;
					adjacencyUL[1]--;

					checkDiagonalMoves(board, lastPosition, adjacencyUR, adjacencyUL, adjacencyDR, adjacencyDL,
							++cWinner, limitUR, ++limitUL, limitDR, limitDL);
				}

			}

		}

		return false;
	}

}
