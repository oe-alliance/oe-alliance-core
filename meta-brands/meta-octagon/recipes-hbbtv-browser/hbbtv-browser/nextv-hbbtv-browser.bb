SUMMARY = "WebKit web rendering engine for the GTK+ platform"
HOMEPAGE = "http://www.webkitgtk.org/"
BUGTRACKER = "http://bugs.webkit.org/"

LICENSE = "BSD & LGPLv2+"
LIC_FILES_CHKSUM = "\
	file://Source/WebCore/rendering/RenderApplet.h;endline=22;md5=fb9694013ad71b78f8913af7a5959680 \
	file://Source/WebKit/gtk/webkit/webkit.h;endline=21;md5=b4fbe9f4a944f1d071dba1d2c76b3351 \
	file://Source/JavaScriptCore/parser/Parser.h;endline=23;md5=2f3cff0ad0a9c486da5a376928973a90 \
	"

DEPENDS = "glib-2.0 zlib enchant libsoup-2.4 curl libxml2 cairo libidn gnutls gtk+ \
           gstreamer1.0 gstreamer1.0-plugins-base flex-native bison-native gperf-native sqlite3 icu"

PR = "r0"


SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/NexTVTeam/hbbtv-browser.git;protocol=https; \
    file://0001-bison-3.patch \
    file://0001-fix-build-with-gcc-6.20.patch \
    file://0001-fix-build-issue-with-cglib-2.2.4.patch \
    file://webkit-gtk_fixed_crash_error.patch \
    file://maketokenizer.patch \
"

inherit autotools lib_package gtk-doc pkgconfig perlnative pythonnative

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
	--enable-debug=no \
	--with-gtk=2.0 \
	--disable-spellcheck \
	--enable-optimizations \
	--disable-channel-messaging \
	--disable-meter-tag \
	--enable-image-resizer \
	--disable-sandbox \
	--disable-xpath \
	--disable-xslt \
	--disable-svg \
	--disable-filters \
	--disable-svg-fonts \
	--disable-video \
	--disable-video-track \
	--with-target=directfb \
	--disable-jit \
	--enable-fast-malloc \
	--enable-shared-workers \
	--enable-workers \
	--enable-javascript-debugger \
	--enable-fast-mobile-scrolling \
	--enable-offline-web-applications \
	"

LDFLAGS += "-Wl,--no-keep-memory -lgthread-2.0"

CXXFLAGS += " -std=gnu++98"

EXTRA_AUTORECONF = " -I Source/autotools "

ARM_INSTRUCTION_SET = "arm"

COMPATIBLE_HOST_mips64n32 = "null"

CONFIGUREOPT_DEPTRACK = ""

do_configure_append() {
	# somethings wrong with icu, fix it up manually
	for makefile in $(find ${B} -name "GNUmakefile") ; do
		sed -i s:-I/usr/include::g $makefile
	done
	# remove hardcoded path to /usr/bin/glib-mkenums
	for makefile in $(find ${B} -name "GNUmakefile") ; do
		sed -i s:/usr/bin/glib-mkenums:glib-mkenums:g $makefile
	done
}

do_install_append() {
        rmdir ${D}${libexecdir}
}

FILES_${PN} = "${libdir}/lib*.so.*"
FILES_${PN}-dev = "${datadir} \
                   ${includedir} \
                   ${libdir}/*.la \
                   ${libdir}/*.so \
                   ${libdir}/pkgconfig"
