SUMMARY = "XML parser with DOM-like AST and XML serialization for TSDuck"
DESCRIPTION = "tsxml is a small, not (yet) spec compliant XML library that contains a liberal parser, \
	a syntax tree similar to, but not as complicated as, the DOM. The syntax tree can be used to build XML manually. \
	It can serialize formatted and compact XML."

require tsduck.inc

DEPENDS = "gettext-native"

inherit gittag pkgconfig native

EXTRA_OEMAKE = 'CXXFLAGS_EXTRA=-Wno-maybe-uninitialized \
				SYSROOT=${D} \
				BINDIR="${B}/bin" \
				NOTEST=1 NOPCSC=1 NODTAPI=1 NOSRT=1 NODOC=1 NOVATEK=1 NOEDITLINE=1 NOCURL=1 NOPCSTD=1'

do_install:append() {
	install -d ${D}${bindir}
	install -m 755 ${B}/bin/tsxml ${D}${bindir}
	install -d ${D}${libdir}
	install -m 755 ${B}/bin/libts*.so ${D}${libdir}
}
