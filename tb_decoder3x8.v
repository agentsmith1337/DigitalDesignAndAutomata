`timescale 1ns/1ps
module tb_decoder3x8;
    wire [7:0] Y;
     reg [2:0] sel;
    decoder3x8 uut(
        .D(sel),
        .Y(Y)
    );
    initial begin 
        $monitor("Time=%0d | sel=%b | out=%b",$time, sel, Y);
            sel=3'b000; #10;
            sel=3'b001; #10;
            sel=3'b010; #10;
            sel=3'b011; #10;
            sel=3'b100; #10;
            sel=3'b101; #10;
            sel=3'b110; #10;
            sel=3'b111; #10;

        $finish;
    end
endmodule