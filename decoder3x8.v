module decoder3x8(output wire [7:0] Y, input wire [2:0] D);
    wire d2n, d1n, d0n;

    not(d2n, D[2]);
    not(d1n, D[1]);
    not(d0n, D[0]);

    and(Y[7], D[2], D[1], D[0]);
    and(Y[6], D[2], D[1], d0n);
    and(Y[5], D[2], d1n, D[0]);
    and(Y[4], D[2], d1n, d0n);
    and(Y[3], d2n, D[1], D[0]);
    and(Y[2], d2n, D[1], d0n);
    and(Y[1], d2n, d1n, D[0]);
    and(Y[0], d2n, d1n, d0n);

endmodule