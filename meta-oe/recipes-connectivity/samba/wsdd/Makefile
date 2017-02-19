
CFLAGS = -Wall -Werror -O2

TARGET = wsdd

OBJS = wsdd.o 

LDFLAGS = -luuid

${TARGET} : $(OBJS)
	${CC} ${CFLAGS} -o ${TARGET} $(OBJS) $(LDFLAGS)

wsdd.o: wsdd.c 
	${CC} ${CFLAGS} -c wsdd.c

clean:
	rm -f ${TARGET} ${OBJS} *~
