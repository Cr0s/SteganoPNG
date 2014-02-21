SteganoPNG
==========

SteganoPNG is a simple tool to steganographically (hidden &amp; secret) encode your file into .PNG image

Usage
==========
 java -jar SteganoPNG.jar -c source.png yourfile.ext KEY output.png
 java -jar SteganoPNG.jar -d stegano.png KEY output.ext

Parameters:
 -c - encrypts your file by specified KEY and encode it into .PNG
 -d - try to decrypt and extract file from steganographic .PNG

Exmaples:
 java -jar SteganoPNG.jar -c picture.png hidden_message.txt SecREtKeyPassWord wa
llpaper.png
 java -jar SteganoPNG.jar -d wallpaper.png SecREtKeyPassWord hidden_message.txt
