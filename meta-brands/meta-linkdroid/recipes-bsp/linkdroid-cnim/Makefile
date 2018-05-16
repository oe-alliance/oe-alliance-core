src = $(wildcard *.c)
obj = $(src:.c=.o)

#LDFLAGS = -ldvbv5
#CFLAGS=-Ilibdvbv5

cnim: $(obj)
	$(CC) -o $@ $^ $(LDFLAGS)

.PHONY: clean
clean:
	rm -f $(obj) cnim
