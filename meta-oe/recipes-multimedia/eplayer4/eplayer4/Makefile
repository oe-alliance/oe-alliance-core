CFLAGS =-O2 -Wall $(pkg-config --cflags --libs gstreamer-0.10)

all: eplayer4

eplayer4: eplayer4.c
	$(CC) -I=${includedir}/gstreamer-0.10 -I=${includedir}/libxml2 -I=${includedir}/glib-2.0 -I=$(libdir)/glib-2.0/include -I=${includedir}/c++ -I=${includedir}/c++/mipsel-oe-linux $(CFLAGS) -DEPLAYER4_STANDALONE eplayer4.c -o $@ -lgstreamer-0.10 -lgobject-2.0 -lglib-2.0

clean:
	-@rm -rf eplayer4)


%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@


prefix=/usr/local
install: eplayer4
	install -D -m 0755 eplayer4 $(DESTDIR)$(prefix)/bin/eplayer4

.PHONY: all clean install

.SILENT: clean