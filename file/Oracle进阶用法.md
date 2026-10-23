## 1 表相关

### 1.1 表结构


#### 1）删除列：
```sql
ALTER TABLE TABLE_NAME DROP COLUMN COLUMN_NAME
```
#### 2）重命名列：
```sql
ALTER TABLE TABLE_NAME RENAME COLUMN OLD_COLUMN_NAME TO NEW_COLUMN_NAME
```
#### 3）修改列类型：
```sql
ALTER TABLE TABLE_NAME MODIFY COLUMN_NAME TYPE
```
#### 4）赋予function执行权限：
```sql
GRANT EXECUTE ON FUNCTION_NAME TO USER_NAME
```
#### 5）赋予查询权限：
```sql
GRANT SELECT ON TABLE_NAME TO USER_NAME

```
#### 6）新增列
```sql
ALTER TABLE employees
ADD (email VARCHAR2(50));
```

### 1.2表内容

#### 1） 删除重复数据，只保留一条：
```sql
DELETE FROM TABLE_NAME
WHERE ROWID NOT IN(
SELECT MIN(T.TOWID) FROM TABLE_NAME T1,TABLE T
WHERE T1.COL1=T.COL1 AND T1.COL2=T.COL2
GROUP BY T.COL1,T.COL2
)
```

#### 2） 有则更新，无则插入 MERGE INTO用法
```sql
MERGE INTO TABLE1 A
USING(TABLE2) B
ON(A.COL1=B.COL1…)
WHEN MATCHED THEN
UPDATE SET A.COL2=B.COL2,A.COL3=B.COL3
WHEN NOT MATCHED THEN
INSERT (COL1, COL2, COL3)VALUES(B. COL1, B.COL2, B.COL3)
```
### 1.3 时间函数
#### 1） 获取星期一~星期日
```sql
TO_CHAR(SYSDATE,'Day')   
```
#### 2） 获取周日到周六   1~7=>星期日~星期六
```sql
TO_CHAR(SYSDATE,'D')     
```

#### 3） 今天是本月的第几周
```sql
TO_CHAR(SYSDATE,'W')
```
#### 4） 今天是本年的第几周，
##### 从1.1开始计算 ww是以今年的一月一号为第一周的第一天。而iw则不是，它是以一月一号到下周日为第一周。
```sql
TO_CHAR(SYSDATE,'WW')
```
#### 5） 今天是本年的第几天
```sql
TO_CHAR(SYSDATE,'DDD')
```
#### 6）今天是本年第几季度
```sql
TO_CHAR(SYSDATE,'Q')
```

#### 7） 当前时间下一个周几
```sql
NEXT_DAY(SYSDATE,1~7)
```

#### 8）上周日
```sql
TRUNC(NEXT_DAY(SYSDATE-8-7.5/24,1)+1)
```
#### 9） 当月末
```sql
LAST_DAY(SYSDATE)
```
#### 10）  上月末
```sql
LAST_DAY(ADD_MONTHS(SYSDATE,-1))      
```
#### 11）  当月初
```sql
TRUNC(ADD_MONTHS(SYSDATE,0),'MM')
```

#### 12）  毫秒时间 
```sql
SYSTIMESTAMP   YYYY-MM-DD HH24:MI:SS.FF
```
#### 13）  时间间隔 
```sql
NUMTODSINTERVAL(1,'DAY')
```
#### 14）  一年的每天：
```sql
SELECT TRUNC(SYSDATE,'YYYY')+ROWNUM-1
FROM DUAL
CONNECT BY ROWNUM<=365;
```

#### 15） add_months 用法
```sql
ADD_MONTHS(SYSDATE-7.5/24,1)
```


## 2   函数具体用法

### 2.1 行转列

#### 1）单个行转列
```sql
SELECT * FROM(
	SELECT LOT_ID,OPE_NO,RN FROM DUAL
)
PIVOT(
	MAX(OPE_NO) FOR RN IN('OP1','OP2','OP3','OP4'……)
)
```

#### 2)多个行转列
```sql
SELECT * FROM(
	SELECT LOT_ID,OPE_NO,RN FROM DUAL
)
PIVOT(
	MAX(A) A,MAX(B) B,MAX(C) C FOR RN IN('OP1','OP2','OP3','OP4'……)
)
```
### 2.2列转行
```sql
SELECT EQP_ID,MOVE FROM(
	SELECT QCK01,QCK21,QCK41…… FROM DUAL
	UNPIVOT(
		MOVE FOR EQP_ID IN('QCK01','QCK21','QCK41'……)
	)
)
```
### 2.3合并行
#### 1） 不可直接去重
```sql
SELECT LISTAGG(X)WITHIN GROUP(ORDER BY Y) FROM DUAL GROUP BY Y  
```
#### 2）可直接去重
```sql
SELECT TO_CHAR(WM_CONCAT(DISTINCT X)) FROM DUAL GROUP BY Y 
```
#### 3）行并列
```sql
SELECT LISTAGG   
FROM(
	--列拆行去重
	SELECT DISTINCT REGEXP_SUBSTR('A;B;C','[^;]+',1,LEVEL) TAG
	FROM DUAL
	CONNECT BY LEVEL<=REGEXP_COUNT('A;B;C','[^;]+')
	UNION      --UNION并集     --MINUS差集    INTERSECT交集
	SELECT DISTINCT REGEXP_SUBSTR('B;C;D','[^;]+',1,LEVEL) TAG
	FROM DUAL
	CONNECT BY LEVEL<=REGEXP_COUNT('B;C;D','[^;]+')
)
GROUP BY 1
```
#### 4）行转列 高效率
```sql
SELECT 
SHIFT_DATE,LOT_ID,OPE_NO,EQP_ID,PROCESS_START_TIME,CLAIM_TIME,CLAIM_MEMO,CH 
FROM(
SELECT 
T.SHIFT_DATE,T.LOT_ID,T.OPE_NO,T.EQP_ID,T.OP_START_DATE_TIME,T.CLAIM_TIME,T.CLAIM_MEMO,T.PROCESS_START_TIME
,TRIM(REGEXP_SUBSTR(t.CH, '[^ ]+', 1, levels.column_value)) AS CH
FROM (
SELECT 
LOT_ID,OPE_NO,EQP_ID,CLAIM_MEMO,SHIFT_DATE,CH,OP_START_DATE_TIME,CLAIM_TIME,PROCESS_START_TIME
FROM(
SELECT 
LOT_ID,OPE_NO,EQP_ID,CLAIM_MEMO,SHIFT_DATE,PROCESS_START_TIME
,REGEXP_REPLACE(REGEXP_REPLACE(CLAIM_MEMO, '[^a-zA-Z0-9|&]', ''), '([|&]+)', ' ')CH
,OP_START_DATE_TIME,CLAIM_TIME
FROM(
SELECT LOT_ID,OPE_NO,EQP_ID,SHIFT_DATE
,CASE WHEN SUBSTR(EQP_ID,1,1) IN ('R','M') THEN NULL ELSE CLAIM_MEMO END CLAIM_MEMO
,OP_START_DATE_TIME,PROCESS_START_TIME,CLAIM_TIME
FROM ODS.V_MOVE_HISTORY 
WHERE EQP_ID IS NOT NULL
AND SHIFT_DATE >= TO_CHAR(SYSDATE - 7.5/24 - 4,'YYYYMMDD')  AND LOT_ID <> '{0}'
))
)T,
  TABLE(CAST(MULTISET(
    SELECT LEVEL
    FROM dual
    CONNECT BY LEVEL <= REGEXP_COUNT(t.CH, ' ') + 1
  ) AS sys.odcinumberlist)) levels
)WHERE (SUBSTR(EQP_ID,1,1)<>'E' OR (SUBSTR(EQP_ID,1,1)='E' AND NVL(CH,1) NOT IN('C','D')))
```


### 2.4 OVER开窗函数
#### 1) 分组中取前第m行记录的x字段，没有返回null
```sql
LAG(X,M,N)OVER(PARTITION BY A ORDER BY B)
```
#### 2) 分组中上一条非空记录的x字段
```sql
LAG(X IGNORE NULLS)OVER(PARTITION BY A ORDER BY B)
```
#### 3) 分组中下一条非空记录的x字段
```sql
LEAD(X IGNORE NULLS)OVER(PARTITION BY A ORDER BY B)
```
#### 4) 分组中第一个值
```sql
FIRST_VALUE(X)OVER(PARTITION BY A ORDER BY B)
```
#### 5) 分组中最后一个值
```sql
LAST_VALUE(X)OVER(PARTITION BY A ORDER BY B)
```
#### 6) 根据s排序，平均分成n组
```sql
NTILE(N)OVER(ORDER BY S)              -- 
```
#### 7)分组中在本条记录之前的最大值，UNBOUNDED不限定，PRECEDING向前偏移，FOLLOWING向后偏移
```sql
MAX(PROCESS_END_TIME)OVER(PARTITION BY MTOOL_ID,CJ_ID ORDER BY PROCESS_END_TIME,PROCESS_START_TIME ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING)
```

### 2.5 时间去重算法
```sql
SELECT EQP_ID,SUM(PRODG_TIME) AS OCS_TIME
FROM( 
    SELECT EQP_ID,TIME_GROUP,MAX(PROCESS_END_TIME),MIN(PROCESS_START_TIME),MAX(PROCESS_END_TIME)-MIN(PROCESS_START_TIME) AS PRODG_TIME
    FROM(
        SELECT PROCESS_START_TIME,PROCESS_END_TIME,EQP_ID,
        SUM(START_FLAG)OVER(PARTITION BY EQP_ID ORDER BY PROCESS_START_TIME) AS TIME_GROUP
        FROM(
            SELECT PROCESS_START_TIME,PROCESS_END_TIME,MTOOL_ID AS EQP_ID,
            CASE WHEN MAX(PROCESS_END_TIME)OVER(PARTITION BY MTOOL_ID,CJ_ID ORDER BY PROCESS_END_TIME,PROCESS_START_TIME
            ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING)>=PROCESS_START_TIME THEN 0 ELSE 1 END AS START_FLAG
            FROM(
                SELECT MTOOL_ID,FOUP_ID,JOB_SPEC_ID,CJ_ID,PJ_ID,PROCESS_ST_TIME AS PROCESS_START_TIME,
                PROCESS_ED_TIME AS PROCESS_END_TIME,JOB_SPEC_GROUP_PO_ID,STATUS
                FROM OCS_PROD.VIEW_RUNRECIPE_MFG@OCSDB
                WHERE TO_CHAR(PROCESS_ST_TIME-7.5/24,'YYYYMMDD')=TO_CHAR(SYSDATE-7.5/24-1,'YYYYMMDD') 
                AND PROCESS_ED_TIME>PROCESS_ST_TIME
                AND MTOOL_ID IN('QCK01','QCK02','QCK21','QCK41','QEK01','QEK02','QCK51','QCK52','QCK53','QCK54','QCK55','QCK56','QCN51','QCN01','QCN02','QEN01') 
            )
        )
    )GROUP BY EQP_ID,TIME_GROUP
)GROUP BY EQP_ID
```

### 2.6    正则表达式
#### 1)a-z,A-Z,0-9
~~~
\w   
~~~
#### 2)非数字字符
~~~
\D  
~~~
#### 3)数字字符（小数整数、负数）
~~~
\d   
~~~
#### 4)整数型（包括负数）
~~~
REGEXP_LIKE(X,'^([\-]?[0-9])+$')
~~~
#### 5)浮点型
~~~
REGEXP_LIKE(X,'^([\-]?[0-9])+\.[0-9]+$')
~~~
#### 6)科学计数法
~~~
REGEXP_LIKE(X,'^([\-]?[0-9]{1}\.[-9]+E[\-]?[0-9])+$')
~~~
#### 7)N+2个字符+3个数字+.+3个数字  i不区分大小写   c区分大小写
~~~
REGEXP_LIKE(X,'N\w{2}\d{3}.\d{3}','i')
~~~
#### 8)查询.所在位置并分为10行
```sql
SELECT REGEXP_INSTR('192.166.0.1','\.',1,LEVEL) FROM DUAL
CONNECT BY LEVEL<=10
```
#### 9取出每个数字
```sql
SELECT REGEXP_SUBSTR('ASRF123412DSFS22','\d',1,LEVEL) FROM DUAL
CONNECT BY LEVEL<=LENGTH('ASRF123412DSFS22')
```
  
#### 10)将3个连续的数字分组，将1、2组交换顺序并加<>
```sql
REGEXP_REPLACE('333111','(\d{3})(\d{3})','<\2\1>')
```

### 2.7   函数
#### 1)强制保留1位小数四舍五入
```sql
TO_CHAR(X,'FM999990.0')
```
#### 2)取s在x中的第几位
```sql
INSTR(X,'S')
```
#### 3)从m位开始，第n次出现s在x中是第几位
```sql
INSTR(X,'S',M,N)
```
#### 4)负数:-1，0 :0，正数:1
```sql
SIGN(X)
```
#### 5)拼接字符串
```sql
CONCAT(X,Y)
```
#### 6)首字母大写
```sql
INITCAP(X)
```
#### 7)在字符串右边增加字符直到补足3位'X**'
```sql
RPAD('X',3,'*')
```
#### 8)在字符串左边增加字符直到补足3位'**X'
```sql
LPAD('X',3,'*')
```
#### 9)返回字符编码最大值
```sql
GREATEST(X,Y,Z)
```
#### 10)返回字符编码最小值
```sql
LEAST(X,Y,Z)
```
#### 11)去除数字
```sql
TRANSLATE('S15X6','#0123456789','#')
```
#### 12)将x中的m替换为n(REPLACE)
```sql
TRANSLATE(X,M,N)
```
#### 13)X的N次方
```sql
POWER(X,N)
```
#### 14) X的N次方根
```sql
SELECT POWER(EXP(1),LN(X)/N) FROM DUAL
```


### 2.8 定时job相关 SHEDULE

#### 1）查询所有定时job
```sql
SELECT * FROM USER_JOBS ORDER BY JOB DESC
```
#### 2） 是否启用job true为关闭
```sql
EXEC DBMS_JOB.BROKEN(1062, TRUE);
```
#### 3） dbeaver用法 是否启用job true为关闭
```sql
DECLARE
  job_number NUMBER := 1643;
  new_status BOOLEAN := TRUE;
BEGIN
  DBMS_JOB.BROKEN(job_number, new_status);
END;
```
#### 4） 修改下次更新时间
```sql
EXEC DBMS_JOB.NEXT_DATE(1062, TO_DATE('20221001 07:36:00','YYYYMMDD HH24:MI:SS'));    
```
#### 5） dbeaver用法 修改下次更新时间
```sql
DECLARE
  job_number NUMBER := 1643;
BEGIN
  SYS.DBMS_JOB.CHANGE(JOB => job_number,
                      WHAT => 'PWEB.PUSH_LOT_DAY_P;',
                      NEXT_DATE => TO_DATE('01-10-2022 07:36:00', 'dd-mm-yyyy hh24:mi:ss'),
                      INTERVAL => 'trunc(sysdate)+7.6/24+1');
  COMMIT;
END;
```
#### 6）修改定时时间
```sql
DECLARE
  job_number NUMBER := 1643;
BEGIN
  SYS.DBMS_JOB.SUBMIT(JOB => job_number,
                      WHAT => 'PWEB.FCST_WIP_DAY_P;',
                      NEXT_DATE => TO_DATE('23-02-2022 07:30:00', 'dd-mm-yyyy hh24:mi:ss'),
                      INTERVAL => 'trunc(sysdate)+7.5/24+1');
  COMMIT;
END;
```

#### 6）查询存储过程 
```sql
SELECT text
    FROM user_source
   WHERE NAME = 'N2_PURGE_SUMMARY_P'
```


## 3 重点逻辑


### 3.1 查询 一般点火、重点点火、空机点火的LOT_ID
```sql
SELECT 
  SUBSTR(KEY1,1,9) AS LOT_ID,
  DECODE(FUNCTION_KEY,'C61G','一般点火','C19E','重点点火','C19E3','空机点火')  LOT_TYPE,
  ROW_NUMBER()OVER(PARTITION BY SUBSTR(KEY1, 1, 9)ORDER BY SHIFT_DATE DESC) RN
FROM
  PWEB.FUNCTION_COMMENT
WHERE
  FUNCTION_KEY IN ('C19E3','C19E','C61G')AND SUBSTR (KEY1,10) = 'MFG'
```

### 3.2 菜单 目录
```sql
INSERT INTO NEXWEB.dbo.FILEINFO
([NO], CATEGORY, TITLE, HTTP, [PATH], PERIOD, RESPONSER, HTTP2,FLAG)
VALUES(87, 'MASTERFLOW', 'Wait Scrap', 'http://n1mfgs04:8080/NewReporting/WaitScrap/index', '', '', '', 'http://n1mfgs04:8080/NewReporting/WaitScrap/index','Y');
```

### 3.3 EQP_ID ,CHAMBER 归属 module,eqp_g 信息
```sql
SELECT * FROM PWEB.EQP_ID_USE_STD;
```
                      
### 3.4 dblink相关
#### 1） A3PDW dblINK
```sql
@TO_A3_PDW 
```
#### 2) dblink 建置
```sql
CREATE DATABASE LINK TO_A3PDW
  CONNECT TO A12_MFG IDENTIFIED BY A3pDwMFG12
  USING '10.53.192.125/A3PPDW';
```  
#### 3） 删除dblink 
```sql
DROP DATABASE LINK TO_A3PDW;
```
#### 4） 查询dblink 
```sql
SELECT * FROM USER_DB_LINKS;
```
## 4  误删除或更新数据处理
### 查询什么时间点的数据
```sql
SELECT * FROM 表名 AS OF timestamp to_timestamp('20240727','YYYYMMDD')
```
### 允许表移动
```sql
ALTER TABLE 表名 ENABLE ROW MOVEMENT
```
### 闪回数据
```sql
flashback table 表名 to timestamp(sysdate-1/24);
```
## 5账号 数据表查询
```sql
SELECT *
FROM all_tab_privs
WHERE grantee = 'RD_PB11_01' -- 将YOUR_USERNAME替换为你的用户名
AND privilege = 'SELECT';
```

## 6锁表查询
```sql
SELECT s.sid, 
       s.serial#,
       s.username,
       a.sql_text AS locked_sql
FROM v$session s
JOIN v$sqlarea a ON s.sql_id = a.sql_id
WHERE s.sid IN (
    SELECT session_id 
    FROM v$locked_object 
    WHERE object_id IN (
        SELECT object_id 
        FROM dba_objects 
        WHERE owner = 'A1MFG'
    )
);

```

## 7 power shell telnet 功能
``` sql
Test-NetConnection -ComputerName example.com -Port 80
```