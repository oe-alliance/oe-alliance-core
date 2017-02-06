SUMMARY = "WebKit web rendering engine for the GTK+ platform"
HOMEPAGE = "http://www.webkitgtk.org/"
BUGTRACKER = "http://bugs.webkit.org/"

LICENSE = "BSD & LGPLv2+"
LIC_FILES_CHKSUM = "\
	file://Source/WebCore/rendering/RenderApplet.h;endline=22;md5=fb9694013ad71b78f8913af7a5959680 \
	file://Source/WebKit/gtk/webkit/webkit.h;endline=21;md5=b4fbe9f4a944f1d071dba1d2c76b3351 \
	file://Source/JavaScriptCore/parser/Parser.h;endline=23;md5=2f3cff0ad0a9c486da5a376928973a90 \
	"

ICU_LIB = "icu"

DEPENDS = "zlib enchant libsoup-2.4 curl libxml2 cairo libidn gnutls gtk+ \
           gstreamer1.0 gstreamer1.0-plugins-base flex-native gperf-native sqlite3 ${ICU_LIB}"

PR = "r1"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/NexTVTeam/hbbtv-browser.git;protocol=https; \
  file://bison-2.6.patch \
  file://webkit-gtk-ANGLE-doesn-t-build-with-bison-3.patch \
  file://webkit-gtk_fixed_crash_error.patch \
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

LDFLAGS += "-Wl,--no-keep-memory"

EXTRA_AUTORECONF = " -I Source/autotools "

ARM_INSTRUCTION_SET = "arm"

COMPATIBLE_HOST_mips64n32 = "null"

CONFIGUREOPT_DEPTRACK = ""

do_configure_append() {
	# somethings wrong with icu, fix it up manually
	for makefile in $(find ${B} -name "GNUmakefile") ; do
		sed -i s:-I/usr/include::g $makefile
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
