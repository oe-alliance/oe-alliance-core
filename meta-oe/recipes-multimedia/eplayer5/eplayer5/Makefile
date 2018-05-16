CFLAGS =-O2 -Wall $(pkg-config --cflags --libs gstreamer-1.0)

all: eplayer5

eplayer5: eplayer5.c
	$(CC) -I=${includedir}/gstreamer-1.0 -I=${includedir}/libxml2 -I=${includedir}/glib-2.0 -I=$(libdir)/glib-2.0/include -I=$(libdir)/gstreamer-1.0/include -I=${includedir}/c++ -I=${includedir}/c++/mipsel-oe-linux $(CFLAGS) -DEPLAYER4_STANDALONE eplayer5.c -o $@ -lgstreamer-1.0 -lgobject-2.0 -lglib-2.0

clean:
	-@rm -rf eplayer5)


%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@


prefix=/usr/local
install: eplayer5
	install -D -m 0755 eplayer5 $(DESTDIR)$(prefix)/bin/eplayer5

.PHONY: all clean install

.SILENT: clean