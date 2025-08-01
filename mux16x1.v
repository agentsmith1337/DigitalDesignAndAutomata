module mux16x1 (input wire [15:0] D,
    input wire [3:0] S,
    output wire Y);

    wire [15:0] and_out;
    wire s0n, s1n, s2n, s3n;

    not (s0n, S[0]);
    not (s1n, S[1]);
    not (s2n, S[2]);
    not (s3n, S[3]);

    and (and_out[15], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[14], D[14], S[3], S[2], S[1], s0n);
    and (and_out[13], D[13], S[3], S[2], s1n, S[0]);
    and (and_out[12], D[12], S[3], S[2], s1n, s0n);
    and (and_out[11], D[11], S[3], s2n, S[1], S[0]);
    and (and_out[10], D[10], S[3], s2n, S[1], s0n);
    and (and_out[9], D[9], S[3], s2n, s1n, S[0]);
    and (and_out[8], D[8], S[3], s2n, s1n, s0n);
    and (and_out[7], D[7], s3n, S[2], S[1], S[0]);
    and (and_out[6], D[6], s3n, S[2], S[1], s0n);
    and (and_out[5], D[5], s3n, S[2], s1n, S[0]);
    and (and_out[4], D[4], s3n, S[2], s1n, s0n);
    and (and_out[3], D[3], s3n, s2n, S[1], S[0]);
    and (and_out[2], D[2], s3n, s2n, S[1], s0n);
    and (and_out[1], D[1], s3n, s2n, s1n, S[0]);
    and (and_out[0], D[0], s3n, s2n, s1n, s0n);

    or (Y, and_out[0], and_out[1], and_out[2], and_out[3], and_out[4], and_out[5], and_out[6], and_out[7], and_out[8],
    and_out[9],and_out[10],and_out[11],and_out[12],and_out[13],and_out[14],and_out[15]);
endmodule