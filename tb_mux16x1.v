`timescale 1ns/1ps

module tb_mux16x1;
    reg [15:0] in;
    reg [3:0] sel;
    wire out;

    mux16x1 uut (
        .D(in),
        .S(sel),
        .Y(out)
    );

    initial begin 
        $monitor("Time=%0d | in=%b | sel=%b | out=%b",$time, in, sel, out);

        in = 16'b1001101001101001; sel = 4'b0001; #10;
        in = 16'b1001101001101001; sel = 4'b0101; #10;
        in = 16'b1001101001101001; sel = 4'b1001; #10;
        in = 16'b1001101001101001; sel = 4'b0110; #10;
        in = 16'b1001101001101001; sel = 4'b1011; #10;
        in = 16'b1001101001101001; sel = 4'b0100; #10;
        in = 16'b1001101001101001; sel = 4'b1010; #10;

        $finish;
    end
endmodule