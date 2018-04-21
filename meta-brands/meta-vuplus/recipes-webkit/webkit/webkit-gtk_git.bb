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

DEPENDS = "zlib enchant libsoup-2.4 curl libxml2 cairo libidn gnutls \
           gtk+ gstreamer1.0 gstreamer1.0-plugins-base flex-native gperf-native  sqlite3 ${ICU_LIB}"

PR = "r5"
PV = "r95199"

BRANCH="vuplus-webkit"
SRCREV="9eed47d1f873a13759d3fd8ead72739b328d710a"

SRC_URI = "git://code.vuplus.com:/git/webkit-r95199-base.git;protocol=http;branch=${BRANCH};rev=${SRCREV} \
    file://0001-bison-3.patch \
    file://0001-fix-build-with-gcc-6.20.patch \
    file://0001-fix-build-issue-with-cglib-2.2.4.patch \
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
}

do_install_append() {
        rmdir ${D}${libexecdir}
        install -d ${D}/usr/bin
        install -m 0755 ${WORKDIR}/build/Programs/GtkLauncher ${D}/usr/bin/webkit.launcher
}

PACKAGES =+ "${PN}-webinspector bjavascriptcore"
FILES_libjavascriptcore = "${libdir}/libjavascriptcoregtk-1.0.so.*"
FILES_${PN}-webinspector = "${datadir}/webkitgtk-*/webinspector/"
FILES_${PN} += "${datadir}/webkitgtk-*/resources/error.html \
                ${datadir}/webkitgtk-*/images \
                ${datadir}/glib-2.0/schemas"

