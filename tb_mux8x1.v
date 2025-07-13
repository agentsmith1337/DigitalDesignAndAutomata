`timescale 1ns / 1ps

module tb_mux8x1;
    reg [7:0] in;
    reg [2:0] sel;
    wire out;

    mux8x1 uut (
        .D(in),
        .S(sel),
        .Y(out)
    );

    initial begin
        $monitor("Time=%0d | in=%b | sel=%b | out=%b", $time, in, sel, out);

        in = 8'b10100001; sel = 3'b000; #10;
        in = 8'b00100010; sel = 3'b001; #10;
        in = 8'b00000100; sel = 3'b010; #10;
        in = 8'b01001000; sel = 3'b011; #10;
        in = 8'b10010010; sel = 3'b011; #10;
        in = 8'b00110000; sel = 3'b100; #10; 
        in = 8'b01100100; sel = 3'b101; #10; 
        in = 8'b01010010; sel = 3'b110; #10; 
        in = 8'b10011100; sel = 3'b111; #10; 
        $finish;
    end
endmodule
