create tablespace tcoin DATAFILE '/u01/app/oracle/oradata/XE/tcoin.dbf' SIZE 10M AUTOEXTEND ON NEXT 10M;
create user tcoin identified by tmonet2k default tablespace tcoin;