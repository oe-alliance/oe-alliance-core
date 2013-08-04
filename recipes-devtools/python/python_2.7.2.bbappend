FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "6"

SRC_URI += " \
			file://some_configure_fixes.patch;striplevel=0 \
			file://fix_pthread_site.patch;striplevel=0 \
			file://no-ldconfig.patch \
			file://ctypes-error-handling-fix.patch \
			file://setuptweaks-2.patch \
			file://pgettext.patch \
			file://no-getaddrinfo-check.patch \
"

EXTRA_OECONF += " \
	ac_cv_file__dev_ptmx=yes \
	ac_cv_file__dev_ptc=no \
	ac_cv_no_strict_aliasing_ok=yes \
	ac_cv_pthread=yes \
	ac_cv_cxx_thread=yes \
	ac_cv_sizeof_off_t=8 \
	--enable-ipv6 \
"

PACKAGES =+ "${PN}-src"
RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*.py"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*/*.py"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*/*/*.py"

# some additional tests
FILES_${PN}-tests += "${libdir}/python${PYTHON_MAJMIN}/*/test* ${libdir}/python${PYTHON_MAJMIN}/*/*/test*"

