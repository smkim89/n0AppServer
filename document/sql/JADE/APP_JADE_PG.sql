DROP TABLE APP_JADE_PG CASCADE CONSTRAINT PURGE;
CREATE TABLE APP_JADE_PG (
	PG_ID	VARCHAR2(8) PRIMARY KEY,
	PG_NM	VARCHAR2(50),
	RE_IP	VARCHAR2(15),
	RE_PORT	VARCHAR2(10),
	USE_YN	CHAR(1),
	REG_DH	VARCHAR2(14),
	UPDT_DH	VARCHAR2(14)
);

COMMENT ON TABLE APP_JADE_PG IS 'JADE : PG 정보';
COMMENT ON COLUMN APP_JADE_PG.PG_ID     IS 'PG 아이디';
COMMENT ON COLUMN APP_JADE_PG.PG_NM     IS 'PG 명';
COMMENT ON COLUMN APP_JADE_PG.RE_IP     IS '결제결과 수신 IP';
COMMENT ON COLUMN APP_JADE_PG.RE_PORT   IS '결제결과 수신 PORT';
COMMENT ON COLUMN APP_JADE_PG.USE_YN    IS '사용여부(1:사용,0:미사용)';
COMMENT ON COLUMN APP_JADE_PG.REG_DH    IS '등록일시';
COMMENT ON COLUMN APP_JADE_PG.UPDT_DH   IS '수정일시';
