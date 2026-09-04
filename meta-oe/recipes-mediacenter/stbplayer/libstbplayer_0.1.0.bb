SUMMARY = "Stable hardware-video backend ABI for STB Kodi"
DESCRIPTION = "A small C ABI, loader and probe tool for machine-specific STB video backends."
HOMEPAGE = "https://github.com/oe-alliance"

PR = "r65"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=45071750435f2d50d503492ef8e003db"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance/stb-kodi-libstbplayer.git;protocol=https;branch=main"

inherit cmake pkgconfig

# The backend is selected and configured from MACHINE/SOC_FAMILY, including
# codec capabilities and platform-specific ABI variants.  Do not let builds
# sharing the same CPU tune overwrite each other in a public package feed.
PACKAGE_ARCH = "${MACHINE}"

# HiSilicon layers use several feature spellings.  SOC_FAMILY is consistent
# across all Kodi-capable HiSi receivers and avoids maintaining model lists.
HISI_STB = "${@'1' if (d.getVar('SOC_FAMILY') or '').startswith('hisi') else '0'}"
BCM_DVB_STB = "${@'1' if (d.getVar('SOC_FAMILY') or '').startswith('bcm') and d.getVar('TARGET_ARCH') in ('arm', 'mipsel') else '0'}"
VUPLUS_MIPSEL_STB = "${@'1' if d.getVar('BRAND_OEM') == 'vuplus' and d.getVar('TARGET_ARCH') == 'mipsel' else '0'}"
DREAM_BCM_STB = "${@'1' if (d.getVar('MACHINE') or '').startswith('dm') and d.getVar('BCM_DVB_STB') == '1' else '0'}"
DREAM_AMLOGIC_STB = "${@'1' if d.getVar('MACHINE') in ('dreamone', 'dreamtwo') and d.getVar('SOC_FAMILY') == 'meson64' else '0'}"
BCM_DVB_VARIANT = "${@'dreambox' if (d.getVar('MACHINE') or '').startswith('dm') else ('gigablue' if (d.getVar('MACHINE') or '') in ('gb7252', 'gb72604', 'vuduo4klite') else ('vuplus' if (d.getVar('MACHINE') or '').startswith('vu') else ('type2' if (d.getVar('MACHINE') or '') in ('xc7362', 'xc7346') else 'normal')))}"
BCM_DVB_CONFIG = "${@d.getVar('DVBMEDIASINK_CONFIG') or ''}"
# These receivers use the HiSilicon Linux-DVB/PES driver path used by E2's
# dvbvideosink. Other HiSilicon families keep the direct AVPLAY backend.
HISI_LINUX_DVB_MACHINES = "hd60 hd61 hd66se pulse4k pulse4kmini multibox multiboxpro multiboxse h8se h9 h9combo h9combose h9se h10 h11 i55plus i55se"

PACKAGECONFIG ??= "${@'hisi-dvb' if d.getVar('HISI_STB') == '1' else ('bcm-dvb' if d.getVar('BCM_DVB_STB') == '1' else ('dream-aml' if d.getVar('DREAM_AMLOGIC_STB') == '1' else ''))}"
PACKAGECONFIG[hisi-dvb] = "-DSTBP_BUILD_HISI_DVB_BACKEND=ON,-DSTBP_BUILD_HISI_DVB_BACKEND=OFF"
PACKAGECONFIG[bcm-dvb] = "-DSTBP_BUILD_BCM_DVB_BACKEND=ON,-DSTBP_BUILD_BCM_DVB_BACKEND=OFF"
PACKAGECONFIG[dream-aml] = "-DSTBP_BUILD_DREAM_AML_BACKEND=ON,-DSTBP_BUILD_DREAM_AML_BACKEND=OFF"

EXTRA_OECMAKE = " \
    -DSTBP_BUILD_TESTS=OFF \
    -DSTBP_DEFAULT_BACKEND_DIR=${libdir}/stbplayer \
    -DSTBP_HISI_MV310=${@'ON' if d.getVar('SOC_FAMILY') == 'hisi3798mv310' else 'OFF'} \
    -DSTBP_HISI_LINUX_DVB=${@'ON' if d.getVar('MACHINE') in d.getVar('HISI_LINUX_DVB_MACHINES').split() else 'OFF'} \
    -DSTBP_BCM_DVB_VARIANT=${BCM_DVB_VARIANT} \
    -DSTBP_BCM_DVB_HAVE_HEVC=${@'ON' if '--with-h265' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_WMV=${@'ON' if '--with-wmv' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_VP6=${@'ON' if '--with-vb6' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_VP8=${@'ON' if '--with-vb8' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_VP9=${@'ON' if '--with-vb9' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_SPARK=${@'ON' if '--with-spark' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_LIMITED_MPEG4V2=${@'ON' if '--with-limited-mpeg4v2' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_NEXUS_STC=${@'ON' if d.getVar('BCM_DVB_STB') == '1' else 'OFF'} \
"

PACKAGES += "${PN}-backend-hisi-dvb ${PN}-backend-bcm-dvb ${PN}-backend-dream-aml"

FILES:${PN}-dev += "${includedir}/stbplayer"
FILES:${PN}-backend-hisi-dvb = "${libdir}/stbplayer/libstbplayer-backend-hisi-dvb.so"
RDEPENDS:${PN}-backend-hisi-dvb = "${PN}"
FILES:${PN}-backend-bcm-dvb = "${libdir}/stbplayer/libstbplayer-backend-bcm-dvb.so"
# The old Vu+ MIPSel DVB driver owns the only full Nexus STC channel and does
# not export an accessor for its decoder-associated SimpleStcChannel.  The
# bridge can therefore load but cannot drive that channel (writes return
# ENODEV).  These receivers use the proven decoder-PTS fallback instead.
# Dreambox BCM kernels expose neither that decoder accessor nor the full Nexus
# STC symbols, so they use the same fallback without installing a dead module.
RDEPENDS:${PN}-backend-bcm-dvb = "${PN}${@' stb-stc-host' if d.getVar('BCM_DVB_STB') == '1' and d.getVar('VUPLUS_MIPSEL_STB') != '1' and d.getVar('DREAM_BCM_STB') != '1' else ''}"
FILES:${PN}-backend-dream-aml = "${libdir}/stbplayer/libstbplayer-backend-dream-aml.so"
RDEPENDS:${PN}-backend-dream-aml = "${PN}"
