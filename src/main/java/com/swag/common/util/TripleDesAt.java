package com.swag.common.util;


public class TripleDesAt {

//	public static final int LOOPCOUNT = 16;
	

	
	public byte shift_left[]= {
			   1, 1, 2, 2, 2, 2, 2, 2,
			   1, 2, 2, 2, 2, 2, 2, 1    };
	

     public byte shift_right []= {
   0, 1, 2, 2, 2, 2, 2, 2,
   1, 2, 2, 2, 2, 2, 2, 1     };


	public	byte ip_table    []= {
			   58, 50, 42, 34, 26, 18, 10,  2,
			   60, 52, 44, 36, 28, 20, 12,  4,
			   62, 54, 46, 38, 30, 22, 14,  6,
			   64, 56, 48, 40, 32, 24, 16,  8,
			   57, 49, 41, 33, 25, 17,  9,  1,
			   59, 51, 43, 35, 27, 19, 11,  3,
			   61, 53, 45, 37, 29, 21, 13,  5,
			   63, 55, 47, 39, 31, 23, 15,  7 };

	public	byte ep_table    []= {
			   32,  1,  2,  3,  4,  5,
			          4,  5,  6,  7,  8,  9,
			                8,  9, 10, 11, 12, 13,
			   12, 13, 14, 15, 16, 17,
			   16, 17, 18, 19, 20, 21,
			   20, 21, 22, 23, 24, 25,
			   24, 25, 26, 27, 28, 29,
			   28, 29, 30, 31, 32,  1 };

	public byte sbox_table [][][]   = {
	{{14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7},
			{0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8},
			{4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0},
	   {15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13}},

	   { {15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10},
			{3, 13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5},
			{0, 14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15},
	   {13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9}},

	   {{10,  0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8},
	   {13,  7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1},
		   { 13,  6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7},
			{1, 10, 13,  0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12}},

			{{7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15},
	   {13,  8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9},
	   {10,  6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4},
			{3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14}},

			{{2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9},
	   {14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6},
			{4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14},
	   {11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3}},

	   {{12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11},
	   {10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8},
			{9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6},
			{4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13}},

			{{4, 11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1},
	   {13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6},
			{1,  4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2},
			{6, 11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12}},
			
				{{ 13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7},
			{1, 15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2},
			{7, 11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8},
			{2,  1, 14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11}}  };
	
	public byte p_table []= {
			   16,  7, 20, 21, 29, 12, 28, 17,
			                1, 15, 23, 26,  5, 18, 31, 10,
			                2,  8, 24, 14, 32, 27,  3,  9,
			   19, 13, 30,  6, 22, 11,  4, 25 };

	//56
	public	byte pc1_table []   = {
			   57, 49, 41, 33, 25, 17,  9,
			    1, 58, 50, 42, 34, 26, 18,
			   10,  2, 59, 51, 43, 35, 27,
			   19, 11,  3, 60, 52, 44, 36,
			   63, 55, 47, 39, 31, 23, 15,
			    7, 62, 54, 46, 38, 30, 22,
			   14,  6, 61, 53, 45, 37, 29,
			   21, 13,  5, 28, 20, 12,  4 };

			  /*                           -[1 1]-[1 1]-     */
	//42
	public	byte pc2_table []  = {
			   14, 17, 11, 24,  1,  5,
			    3, 28, 15,  6, 21, 10,
			   23, 19, 12,  4, 26,  8,
			   16,  7, 27, 20, 13,  2,
			   41, 52, 31, 37, 47, 55,
			   30, 40, 51, 45, 33, 48,
			   44, 49, 39, 56, 34, 53,
			   46, 42, 50, 36, 29, 32 };


	public byte org_key1[] ={0x00,0x00,0x00,0x00,0x00,0x00,0x0F,0x0F};
	public byte org_key2[] ={0x00,0x00,0x00,0x00,0x00,0x00,0x0A,0x0A};
	
	public static final int BLOCK_SIZE = 64;
	public static final int HALF_BLOCK = 32;
	public static final int EP_SIZE = 48;
	public static final int LOOPCOUNT = 16;
	public static final int QUARTET = 4;
	public static final int SEXTET = 6;
	public static final int OCTET = 8;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int PC1_SIZE = 56;
	public static final int PC2_SIZE = 48;
	public static final int HALF_PC1 = 28;
	public static final int ENCIPHER = 1;
	public static final int DECIPHER = 0;
	
	
	public static final int DEFAULT = 1;
	public static final int INVERT = 0;




    public  byte[] changeByte(byte[] byteData, int start){
		
		byte[] retData = new byte[byteData.length-start];
		int i, j;
		for( i=start,  j = 0; i < byteData.length; i++, j++){
			retData[j] = byteData[i];
		}
		return retData;
	}
	
	public static byte[] changeCharToByte(char[] charData, int start){
		
		byte[] retData = new byte[charData.length-start];
		int i, j;
		for( i=start,  j = 0; i < charData.length; i++, j++){
			retData[j] = (byte)charData[i];
		}
		return retData;
	}
	
	public static byte[] changeCharToByte(char[] charData, int start, int length){
		
		byte[] retData = new byte[length];
		int i, j;
		for( i=start,  j = 0; i < length; i++, j++){
			retData[j] = (byte)charData[i];
		}
		return retData;
	}
	
	public static char[] changeByteToChar(byte[] charData, int start){
		
		char[] retData = new char[charData.length-start];
		int i, j;
		for( i=start,  j = 0; i < charData.length; i++, j++){
			retData[j] = (char)charData[i];
		}
		return retData;
	}
	

	public void des_strcpy(byte[] dest, byte[] src, int len){
		des_strcpy(dest, 0, src, 0, len);
	}
	
	public void des_strcpy(char[] dest, char[] src, int len){
		des_strcpy(dest, 0, src, 0, len);
	}
	
	public byte[] des_strcpy(byte[] src, int start, int len){
		byte[] dest = new byte[len];
		int i, j;
		for (i = start, j=0; i< len; i++, j++){
			dest[j] = src[i];
		}
		return dest;
	}
	
	
	public void des_strcpy(byte[] dest, int jstart, byte[] src, int start, int len){
	
		int i, j;
		int srclen = src.length - start;
		int destlen = dest.length - jstart;
		if(srclen < len) len = srclen;
		if(destlen < len) len = destlen;
		
		int max = start + len;
		for (i = start, j=jstart; i< max; i++, j++){
			dest[j] = src[i];
		}
		//System.out.println("xx");
		//return dest;
	}
	
	public void des_strcpy(char[] dest, int jstart, char[] src, int start, int len){
		
		int i, j;
		int srclen = src.length - start;
		int destlen = dest.length - jstart;
		if(srclen < len) len = srclen;
		if(destlen < len) len = destlen;
		int max = start + len;
		for (i = start, j=jstart; i< max; i++, j++){
			dest[j] = src[i];
		}
		//return dest;
	}
	
	
	public int des_strlen(byte[] str){
		return str.length;
	}
	
	public void des_self_permute(byte[] refer, byte[] dest, int mode, int max)
	{
		int i, pos;
		byte src[] = new byte[BLOCK_SIZE] ; //64로 할당해야함		
		des_strcpy(src,  dest,  max);
		//des_strcpy(src, dest, max);
		for(i=0; i< max; i++){
			pos = refer[i];
			if(mode == DEFAULT) dest[i] = src[pos-1];
			else dest[pos-1] = src[i];
		}
	}
	
	public void des_permute(byte[] refer, byte[] dest, byte[] src, int max)
	{
		
	
		int i, pos;
		for(i=0; i< max; i++){
			pos = refer[i];
			dest[i] = src[pos-1];
		}
		
	}
	
	public void des_xor(byte[] dest, byte[] src, int max)
	{
		int i;
		for(i=0; i< max; i++) dest[i] ^= src[i];
	}
	
	public void des_sbox_substitute(byte[] dest, byte[] src, int num){
		int i = 0, row = 0, result = 0, column = 0;
		row = (src[0] << 1) | src[5];
		for(i=1; i<5; i++){
			column <<= 1;
			column |= src[i];
		}
	//	System.out.println("zz" + num + " " + row + " " + column);
		result = sbox_table[num][row][column];
		for(i=0;i<QUARTET;i++){
			dest[i] = (byte)((result & 0x08) >> 3);
			result <<= 1;
		}
	}
	
	public void des_key_shift_left(byte[] left_key, byte[] right_key, int num){
		int i, j, count, msb;
		byte temp_left, temp_right;
		msb = HALF_PC1 -1;
		count = shift_left[num];
		
		for(i = 0; i < count; i++){
			temp_left = left_key[0];
			temp_right = right_key[0];
			
			
				for(j = 0; j< msb; j++){
					left_key[j] = left_key[j+1];
					right_key[j] = right_key[j+1];
				}
				left_key[msb] = temp_left;
				right_key[msb] = temp_right;
				
			
		}
		
	}
	
	public void des_key_shift_right(byte[] left_key, byte[] right_key, int num)
	{
		int i, j, count, lsb;
		byte temp_left, temp_right;
		
		lsb = HALF_PC1 -1;
		count = shift_right[num];
		for(i = 0; i< count; i++){
			temp_left = left_key[lsb];
			temp_right = right_key[lsb];
			for(j = lsb; j > 0; j--){
				left_key[j] = left_key[j-1];
				right_key[j] = right_key[j-1];
			}
			left_key[0] = temp_left;
			right_key[0] = temp_right;
		}
		
	}
	
	public void des_hex2bin( byte[] dest, byte[] src, int size)
	{
		int i, j, count=0;
		byte hexa[] = new byte[BLOCK_SIZE];
		
		if(size < 1 || size > 8) return;

		des_strcpy(hexa, src, 8*size);
		
		
		for(i = 0;  i <size; i++){
			for(j=0; j<8; j++){ 
					dest[count++] = (byte)((hexa[i] & 0x80) >> 7);
					hexa[i] <<= 1;
			}
		}
	}
	
	public void des_bin2hex(byte[] dest, byte[] src, int size)
	{
		int i, j, count=0;
		if(size < 1 || size > 8) return;
		for(i=0; i< size; i++){ 
			for(j=0; j<8; j++) dest[i] = (byte)(dest[i] << 1 | src[count++]);
		}
	}
	
	
	class Union{
		
		public void makeParent(byte[][] dest, byte[] src){
			int srcLength = src.length;
			int destLength = dest[0].length;
			int mod = srcLength/destLength;
			for(int i = 0 ; i < mod; i++ ){
				des_strcpy(dest[i], 0, src, destLength * i, destLength );
			}	
		}
		
		public void makeParentR(byte[][] dest, byte[] src){
			int srcLength = src.length;
			int destLength = dest[0].length;
			int mod = srcLength/destLength;
			for(int i = 0 ; i < mod; i++ ){
				des_strcpy(src, destLength * i, dest[i],0,  destLength );
			}	
		}
	}
	class Info extends Union{
		byte[] full = new byte[BLOCK_SIZE];
		byte[][] half = new byte[2][HALF_BLOCK];
		
		public void make(){
			makeParent(half, full);
		}
		public void makeR(){
			makeParentR(half, full);
		}
	}
	
	class Temp  extends Union{
		byte[] full = new byte[HALF_BLOCK];
		byte[][] sbox = new byte[OCTET][QUARTET];
		
		public void make(){
			makeParent(sbox, full);
		}
		public void makeR(){
			makeParentR(sbox, full);
		}
	}
	
	class Key extends Union{
		byte[] full = new byte[PC1_SIZE];
		byte[][] half = new byte[2][HALF_PC1];
		public void make(){
			makeParent(half, full);
		}
		public void makeR(){
			makeParentR(half, full);
		}
	}
	
	class Exp  extends Union{
		byte[] right = new byte[EP_SIZE];
		byte[][] sbox = new byte[OCTET][SEXTET];
		public void make(){
			makeParent(sbox, right);
		}
		public void makeR(){
			makeParentR(sbox, right);
		}
	}
	
	public void comm_des8_key1(byte[] plain, int mode){
		int i, j, k;
		
		
		Info info = new Info();
		Temp temp = new Temp();
		Key key = new Key();
		Exp exp = new Exp();
		
		byte[] chosen_key = new byte[EP_SIZE];
		
		des_hex2bin(info.full, org_key1, 8);
		des_permute(pc1_table, key.full, info.full, PC1_SIZE);   //key.full
		des_hex2bin(info.full, plain, 8);	
		des_self_permute(ip_table, info.full, DEFAULT, BLOCK_SIZE);


		info.make();
		key.make();


		for(i=0; i<LOOPCOUNT;i++){

			des_permute(ep_table, exp.right, info.half[RIGHT], EP_SIZE);
			exp.make();
			
			if(mode == ENCIPHER) des_key_shift_left(key.half[0], key.half[1], i);
			else des_key_shift_right(key.half[0], key.half[1], i);
			
			key.makeR();
			
			des_permute(pc2_table, chosen_key, key.full, PC2_SIZE);
			des_xor(exp.right, chosen_key, EP_SIZE);
					
			exp.make();
			
			for(j=0; j<OCTET; j++){
				des_sbox_substitute(temp.sbox[j], exp.sbox[j], j);
			}
			temp.makeR();
			des_self_permute(p_table, temp.full, DEFAULT, HALF_BLOCK);
			des_xor(temp.full, info.half[LEFT], HALF_BLOCK);
			des_strcpy(info.half[LEFT], info.half[RIGHT], HALF_BLOCK);
			des_strcpy(info.half[RIGHT], temp.full, HALF_BLOCK);
			info.makeR();
			
		}
		  des_strcpy(temp.full, info.half[LEFT], HALF_BLOCK);
		  
		  des_strcpy(info.half[LEFT], info.half[RIGHT], HALF_BLOCK);
		  des_strcpy(info.half[RIGHT], temp.full, HALF_BLOCK);
		  info.makeR();
		  des_self_permute(ip_table, info.full, INVERT, BLOCK_SIZE);
		  des_bin2hex(plain, info.full, 8);		
	}
	
	public void comm_des8_key2(byte[] plain, int mode){
		
		int i, j, k;
		
		Info info = new Info();
		Temp temp = new Temp();
		Key key = new Key();
		Exp exp = new Exp();
	
	
		byte[] chosen_key = new byte[EP_SIZE];
		des_hex2bin(info.full, org_key2, 8);
		  des_permute(pc1_table, key.full, info.full, PC1_SIZE);
		  des_hex2bin(info.full, plain, 8);
		  des_self_permute(ip_table, info.full, DEFAULT, BLOCK_SIZE);
		  info.make();
		  key.make();
			for(i=0;i<LOOPCOUNT;i++) {
				des_permute(ep_table, exp.right, info.half[RIGHT],EP_SIZE);
				exp.make();
			
				if(mode == ENCIPHER ) des_key_shift_left(key.half[0], key.half[1], i);
				else des_key_shift_right(key.half[0], key.half[1], i);
				key.makeR();
				
				des_permute(pc2_table, chosen_key, key.full, PC2_SIZE);
				des_xor(exp.right, chosen_key, EP_SIZE);
				exp.make();
				
				for(j=0;j<OCTET;j++) {
			  	des_sbox_substitute(temp.sbox[j], exp.sbox[j], j);
				}
				temp.makeR();
			
				des_self_permute(p_table, temp.full, DEFAULT, HALF_BLOCK);
				des_xor(temp.full, info.half[LEFT], HALF_BLOCK);
				des_strcpy(info.half[LEFT], info.half[RIGHT], HALF_BLOCK);
				des_strcpy(info.half[RIGHT], temp.full, HALF_BLOCK);
				info.makeR();
		  }
		  
		  des_strcpy(temp.full, info.half[LEFT], HALF_BLOCK);
		  des_strcpy(info.half[LEFT], info.half[RIGHT], HALF_BLOCK);
		  des_strcpy(info.half[RIGHT], temp.full, HALF_BLOCK);
		  info.makeR();
		  des_self_permute(ip_table, info.full, INVERT, BLOCK_SIZE);
		  des_bin2hex(plain, info.full, 8);
	}
	
	
	public void des_lfsr(byte[] init_key)
	{
		int i, j;
		byte bin_f, coef[] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		  byte[] bin_s = new byte[BLOCK_SIZE];
		  byte[] bin_coef = new byte[BLOCK_SIZE];
		  des_strcpy(bin_s, init_key, BLOCK_SIZE);
		  
		  for(i=0; i<BLOCK_SIZE; i++)
		  {
				init_key[i]=bin_s[0];
				bin_f = (byte) (bin_coef[0] * bin_s[0]);
			
				for(j=1; j<BLOCK_SIZE; j++)
				{
			  	bin_f= (byte) (bin_coef[j] * bin_s[j]);
			 		bin_s[j-1]= bin_s[j];
				}
				
				bin_s[BLOCK_SIZE-1]=   bin_f ;
		  }
		
		
	}

	public void mod_des_key1(byte[] plain, int len)
{
  int  i;
  byte[] hex_key = new byte[8];
  byte[] bin_plain = new byte[BLOCK_SIZE];
  byte[] bin_key = new byte[BLOCK_SIZE];

  des_hex2bin(bin_key, org_key1, 8);
  des_lfsr(bin_key);
  des_bin2hex(hex_key, bin_key, 8);
  comm_des8_key1(hex_key, ENCIPHER);
  des_hex2bin(bin_key, hex_key, 8);

  des_hex2bin(bin_plain, plain, len);

  for(i=0; i < 8*len; i++) {
  	 bin_plain[i] ^= bin_key[i];
	}
  des_bin2hex(plain, bin_plain, len);
}

	public void mod_des_key2(byte[] plain, int len)
{
  int  i;
  byte[]  hex_key = new byte[8];
  byte[] bin_plain = new byte[BLOCK_SIZE];
  byte[] bin_key = new byte[BLOCK_SIZE];

  des_hex2bin(bin_key, org_key2, 8);
  des_lfsr(bin_key);
  des_bin2hex(hex_key, bin_key, 8);
  comm_des8_key2(hex_key, ENCIPHER);
  des_hex2bin(bin_key, hex_key, 8);

  des_hex2bin(bin_plain, plain, len);

  for(i=0; i < 8*len; i++) {
  	  bin_plain[i]^= bin_key[i];
	}
	
  des_bin2hex(plain, bin_plain, len);
}

public char[] des_key1(char[] src, int mode, int len)
{
  int  loopcnt, mo, i;
  byte[] temp_plain = new byte[8];

  loopcnt = len / 8;
  mo = len % 8;

  byte[] srcb = changeCharToByte(src, 0, src.length);

  for(i=0; i<loopcnt; i++)
  {
		des_strcpy(temp_plain, 0, srcb, i*8, 8);
		comm_des8_key1(temp_plain, mode);
		des_strcpy(srcb, i*8, temp_plain, 0, 8);
  }
   
 
  if(mo != 0)
  {
		des_strcpy(temp_plain, 0, srcb, loopcnt*8, mo);
		mod_des_key1(temp_plain, mo);
		des_strcpy(srcb, loopcnt*8, temp_plain, 0, mo);
  }
  src = changeByteToChar(srcb,  0);
  return src;
}

public char[] des_key2(char[] src, int mode, int len)
{
  int  loopcnt, mo, i;
  byte[] temp_plain = new byte[8];

  loopcnt = len / 8;
  mo = len % 8;
  
  byte[] srcb = changeCharToByte(src, 0, src.length);
  
  for(i=0; i<loopcnt; i++)
  {
		des_strcpy(temp_plain, 0, srcb, i*8, 8);
		comm_des8_key2(temp_plain, mode);
		des_strcpy(srcb, i*8, temp_plain, 0, 8);
  }
  
  if(mo != 0)
  {
		des_strcpy(temp_plain, 0, srcb, loopcnt*8, mo);
		mod_des_key2(temp_plain, mo);
		des_strcpy(srcb, loopcnt*8, temp_plain, 0, mo);
  }
  src = changeByteToChar(srcb, 0);
  return src;
}
	
public static byte[] HexStringToBytes(String hexstr, int inlen){
	
	byte[] ret = new byte[inlen];
	try{
		  ret = hexStringToBytes(hexstr);
	} catch(Exception e){
		System.out.println("decrypt error");
	}
	return ret;
}

public static byte[] hexStringToBytes(String digits) throws IllegalArgumentException, NumberFormatException {
    if (digits == null) { return null; }
    int length = digits.length();
    if (length % 2 == 1) { throw new IllegalArgumentException("For input string: \"" + digits + "\""); }
    length = length / 2;
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
        int index = i * 2;
        bytes[i] = (byte) (Short.parseShort(digits.substring(index, index + 2), 16));
    }
    return bytes;
}


	public void PrintData(char[] buf, int size){
		int i;
		for(i = 0; i< size; ++i){
			System.out.println(buf[i]);
		}
	
	}
	
    public static String format(String src, int width, char fillChar, boolean leftJustify)
    {
            byte[] b = src.getBytes();
            return format(b, width, fillChar, leftJustify);
    }

    public static String format(byte[] src, int width, char fillChar, boolean leftJustify)
    {
            int i, j;
            byte[] b = new byte[width];
            if (src == null)
            {
                    for (i = 0; i < width; i++)
                    {
                            b[i] = (byte)fillChar;
                    }
            }
            else
            {
                    if (leftJustify == true)
                    {
                            for (i = 0, j = 0; i < width; i++)
                            {
                                    if (i < src.length)
                                    {
                                            b[i] = src[j++];
                                    }
                                    else
                                    {
                                            b[i] = (byte)fillChar;
                                    }
                            }
                    }
                    else
                    {
                            for (i = 0, j = 0; i < width; i++)
                            {
                                    if (i < width - src.length)
                                    {
                                            b[i] = (byte)fillChar;
                                    }
                                    else
                                    {
                                            b[i] = src[j++];
                                    }
                            }
                    }
            }
            return new String(b);
    }

	
    public static String dump16(byte[] b, int off, int len)
    {
            StringBuffer sb = new StringBuffer();
            int line = len / 16 + 1;
            String s;
            for (int i = 0; i < line; i++)
            {
                    sb.append(format(Integer.toHexString(i*16), 8, '0', false));
                    sb.append(' ');
                    for (int j = 0; j < 8; j++)
                    {
                            int k = i*16 + j*2;
                            if (k < len)
                            {
                                    s = Integer.toHexString((int)b[off+k+0]&0xff);
                                    sb.append(format(s, 2, '0', false));
                            }
                            else
                            {
                                    sb.append("00");
                            }
                            if (k+1 < len)
                            {
                                    s = Integer.toHexString((int)b[off+k+1]&0xff);
                                    sb.append(format(s, 2, '0', false));
                            }
                            else
                            {
                                    sb.append("00");
                            }
                            sb.append(' ');
                    }
                    sb.append(' ');
                    StringBuffer sb2 = new StringBuffer();
                    for (int j = 0; j < 16; j++)
                    {
                            int k = i*16 + j;
                            if (k < len)
                            {
                                    char c = (char)((int)b[off+k]&0xff);
                                    if (c < 0x20)
                                    {
                                            sb2.append('.');
                                    }
                                    else
                                    {
                                            sb2.append(c);
                                    }
                            }
                            else
                            {
                                    sb2.append('.');
                            }
                    }
                    s = sb2.toString();
                    sb.append(s);
                    sb.append('\n');
            }
            sb.append(len);
            sb.append('\n');
            return sb.toString();
    }

    public static String dump16String(byte[] b, int off, int len)
    {
            StringBuffer sb = new StringBuffer();
            String s;
            for( int i=0; i< len; i++){
            	 s = Integer.toHexString((int)b[off+i]&0xff);
            	 sb.append(format(s, 2, '0', false));
            }
            return sb.toString();
    }
	
	
    public static String charToString16(char[] data, int length){
		return dump16String(changeCharToByte(data, 0), 0, length).toUpperCase();
	}
    
    
	public static void printLine(char[] data){
		System.out.println(dump16(changeCharToByte(data, 0), 0, data.length));
	}
	public static void printLine(byte[] data){
		System.out.println(dump16(data, 0, data.length));
	}
	
	public static void printLine(String data){
		System.out.println(dump16(data.getBytes(), 0, data.length()));
	}
	
	public static void printLine(char[] data, String jusuk){
		System.out.println(jusuk);
		System.out.println(dump16(changeCharToByte(data, 0), 0, data.length));
	}
	public static void printLine(byte[] data, String jusuk){
		System.out.println(jusuk);
		System.out.println(dump16(data, 0, data.length));
	}
	
	//p1 encrypt string   p3 : mode p4 return
	public   String ENDECRYPT(char[] p1, int len, String p3){
		int i;
		
		char[] str;// = new char[2048];
		char[] buf;// = new char[2048];
		char[] buf2;// = new char[2048];
		char[] hinput;// = new char[4096];
		
		byte[] bufb = new byte[2048];
		String result = "";
		
		//len = p1.length;
		if(p3.equals("F") || p3.equals("f")){
			str = p1;
			str = des_key1(str, ENCIPHER, p1.length);
			//printLine(str, "key1");
			buf = str; 
			buf = des_key2(buf, DECIPHER, p1.length);
			str = buf;
			str = des_key1(str, ENCIPHER, p1.length);
			/* testprint*/
		//	System.out.println("print encrypted result hex\n");
			result = charToString16(str, len);

		} else if(p3.equals("B") || p3.equals("b")){
			
			// org_key1 으로 복호화
			
			hinput = p1;
			bufb = HexStringToBytes (String.valueOf(hinput), len/2 );
			buf = changeByteToChar(bufb, 0);
			str = buf;
			str = des_key1(str , DECIPHER, len/2);
			// org_key2 으로 암호화
			str = des_key2(str , ENCIPHER, len/2 );
			buf = str;
			// org_key1 으로 복호화
			//memcpy(buf, str , len/2);
			//*(buf+len/2) = '\0';
			buf = des_key1(buf , DECIPHER, len / 2 );
			result = String.valueOf(buf);
			
		} else {
			
		}
		return result;
		
	}
	
	
	public   String ENDECRYPT(String p1, String p3){
		return ENDECRYPT(p1.toCharArray(), p1.length(), p3 );
	}
	
	
	public static String encrypt(String p1){
		TripleDesAt tirpleDesShinhan = new TripleDesAt();
		return tirpleDesShinhan.ENDECRYPT(p1, "F");
	}
	public static String decrypt(String p1){
		TripleDesAt tirpleDesShinhan = new TripleDesAt();
		return tirpleDesShinhan.ENDECRYPT(p1, "B");
	}
	
	public static String encrypt(String p1, String key1, String key2){
		TripleDesAt tirpleDesShinhan = new TripleDesAt();
		tirpleDesShinhan.setOrgKey1(key1);
		tirpleDesShinhan.setOrgKey2(key2);
		return tirpleDesShinhan.ENDECRYPT(p1, "F");
	}
	public static String decrypt(String p1, String key1, String key2){
		TripleDesAt tirpleDesShinhan = new TripleDesAt();
		tirpleDesShinhan.setOrgKey1(key1);
		tirpleDesShinhan.setOrgKey2(key2);
		return tirpleDesShinhan.ENDECRYPT(p1, "B");
	}
	
	public void setOrgKey1(String key1){
		org_key1 = HexStringToBytes (String.valueOf(key1), key1.length()/2 );
	}
	
	public void setOrgKey2(String key2){
		org_key2 = HexStringToBytes (String.valueOf(key2), key2.length()/2 );
	}
	
	
	
	public static void main(String[] args) {
		
		String temp = "01092655517";

		String encrypt;
		String decrypt;
		
		String key1 = "1BA63C9BC71A1D94";    //0~F로 셋팅 해야함 hexa 코드임!!!
		String key2 = "E0733D371C1A9359";
		encrypt = TripleDesAt.encrypt(temp, key1, key2);
	    decrypt = TripleDesAt.decrypt(encrypt, key1, key2);
	    
	    System.out.println("encrypt : " + encrypt);
	    System.out.println("decrypt : " + decrypt);
	    
	    
	}
}

