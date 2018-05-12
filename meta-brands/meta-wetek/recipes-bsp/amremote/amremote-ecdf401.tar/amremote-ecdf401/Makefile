DEPS = remote_config.h keydefine.h
OBJ = irremote.o config.o parsefile.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

remotecfg: $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS)

.PHONY: clean

clean:
	rm -f *.o *~ remotecfg

.PHONY: install

install:
	cp -f remotecfg $(TARGET_DIR)/usr/bin/
	cp -f remote.conf $(TARGET_DIR)/etc

.PHONY: uninstall

uninstall:
	rm -f $(TARGET_DIR)/usr/bin/remotecfg
