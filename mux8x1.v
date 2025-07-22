module mux8x1 (
    input wire [7:0] D,        // 8 input lines: D0 to D7
    input wire [2:0] S,        // 3-bit select lines: S0, S1, S2
    output wire Y              // Output
);

    wire [7:0] and_out;
    wire s0n, s1n, s2n;

    // Invert select lines
    not (s0n, S[0]);
    not (s1n, S[1]);
    not (s2n, S[2]);

    // Each AND gate represents one data input enabled by the correct select line combination
    and (and_out[0], D[0], s2n, s1n, s0n); // 000
    and (and_out[1], D[1], s2n, s1n, S[0]); // 001
    and (and_out[2], D[2], s2n, S[1], s0n); // 010
    and (and_out[3], D[3], s2n, S[1], S[0]); // 011
    and (and_out[4], D[4], S[2], s1n, s0n); // 100
    and (and_out[5], D[5], S[2], s1n, S[0]); // 101
    and (and_out[6], D[6], S[2], S[1], s0n); // 110
    and (and_out[7], D[7], S[2], S[1], S[0]); // 111

    // OR all AND outputs to get final output
    or (Y, and_out[0], and_out[1], and_out[2], and_out[3],
            and_out[4], and_out[5], and_out[6], and_out[7]);

endmodule
