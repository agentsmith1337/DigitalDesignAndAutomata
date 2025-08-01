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
    and (and_out[14], D[15], S[3], S[2], S[1], s0n);
    and (and_out[13], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[12], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[11], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[10], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[9], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[8], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[7], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[6], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[5], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[4], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[3], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[2], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[1], D[15], S[3], S[2], S[1], S[0]);
    and (and_out[0], D[15], S[3], S[2], S[1], S[0]);

    or (Y, and_out[0], and_out[1], and_out[2], and_out[3], and_out[4], and_out[5], and_out[6], and_out[7], and_out[8]
    and_out[9],and_out[10],and_out[11],and_out[12],and_out[13],and_out[14],and_out[15]);
endmodule