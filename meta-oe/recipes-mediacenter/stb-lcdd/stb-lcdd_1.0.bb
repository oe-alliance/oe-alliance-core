SUMMARY = "LCDproc-compatible front panel bridge for Enigma2 receivers"
DESCRIPTION = "Minimal LCDd protocol server which renders Kodi information directly to STB color, monochrome, text and seven-segment front panels while Enigma2 is stopped."

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

PR = "r11"

SRC_URI = "file://stb-lcdd.c"

S = "${UNPACKDIR}"

inherit pkgconfig

DEPENDS = "freetype harfbuzz libpng"

# The generated hardware description differs even between receivers using the
# same CPU tune, so it must never be reused as a generic architecture package.
PACKAGE_ARCH = "${MACHINE}"

STB_LCD_MODE = "${@'color' if any(x.startswith('colorlcd') or x.startswith('bwlcd') or x.startswith('gigabluelcd') or x == 'displayvfd' for x in (d.getVar('MACHINE_FEATURES') or '').split()) else 'text' if 'textlcd' in (d.getVar('MACHINE_FEATURES') or '').split() else '7segment' if '7segment' in (d.getVar('MACHINE_FEATURES') or '').split() else 'none'}"
RDEPENDS:${PN} = "${@'ttf-dejavu-sans' if d.getVar('STB_LCD_MODE') == 'color' else ''}"
STB_LCD_FORMAT = "native"
STB_LCD_FORMAT:dreamtwo = "rgb565-byteswap"
STB_LCD_FORMAT:dm900 = "dm9x0"
STB_LCD_FORMAT:dm920 = "dm9x0"
STB_LCD_FORMAT:7000s = "rgb565-bitorder"
STB_LCD_FORMAT:7100s = "rgb565-bitorder"
STB_LCD_FORMAT:7105s = "rgb565-bitorder"
STB_LCD_FORMAT:7200s = "rgb565-bitorder"
STB_LCD_FORMAT:7205s = "rgb565-bitorder"
STB_LCD_FORMAT:7210s = "rgb565-bitorder"
STB_LCD_FORMAT:7215s = "rgb565-bitorder"
STB_LCD_FORMAT:7300s = "rgb565-bitorder"
STB_LCD_FORMAT:7400s = "rgb565-bitorder"
STB_LCD_FORMAT:vuduo2 = "native"
STB_LCD_FORMAT:vuduo4k = "bgra"
STB_LCD_FORMAT:vuduo4kse = "bgra"
STB_LCD_FORMAT:vuultimo4k = "bgra"
STB_LCD_FORMAT:vuuno4kse = "bgra"
STB_LCD_FORMAT:vusolo4k = "bgra"
STB_LCD_FORMAT:8100s = "framebuffer"
STB_LCD_FORMAT:et8500 = "framebuffer"

STB_LCD_DEVICE = "auto"
STB_LCD_DEVICE:vuduo2 = "/dev/dbox/oled0"
STB_LCD_DEVICE:8100s = "/dev/fb1"
STB_LCD_DEVICE:et8500 = "/dev/fb1"
STB_LCD_WIDTH = "0"
STB_LCD_HEIGHT = "0"
STB_LCD_BPP = "0"
STB_LCD_COLUMNS = "${@'4' if d.getVar('STB_LCD_MODE') == '7segment' else '0'}"

STB_LCD_SECONDARY_MODE = "none"
STB_LCD_SECONDARY_FORMAT = "native"
STB_LCD_SECONDARY_DEVICE = "auto"
STB_LCD_SECONDARY_WIDTH = "0"
STB_LCD_SECONDARY_HEIGHT = "0"
STB_LCD_SECONDARY_BPP = "0"
STB_LCD_SECONDARY_COLUMNS = "0"

do_compile() {
    ${CC} ${CFLAGS} ${CPPFLAGS} `pkg-config --cflags freetype2 harfbuzz libpng` \
        ${UNPACKDIR}/stb-lcdd.c -o ${B}/stb-lcdd \
        ${LDFLAGS} `pkg-config --libs freetype2 harfbuzz libpng`
}

do_install() {
    install -d ${D}${bindir} ${D}${sysconfdir}
    install -m 0755 ${B}/stb-lcdd ${D}${bindir}/stb-lcdd
    printf '%s\n' \
        'machine=${MACHINE}' \
        'mode=${STB_LCD_MODE}' \
        'pixel_format=${STB_LCD_FORMAT}' \
        'device=${STB_LCD_DEVICE}' \
        'width=${STB_LCD_WIDTH}' \
        'height=${STB_LCD_HEIGHT}' \
        'bpp=${STB_LCD_BPP}' \
        'columns=${STB_LCD_COLUMNS}' \
        'secondary_mode=${STB_LCD_SECONDARY_MODE}' \
        'secondary_pixel_format=${STB_LCD_SECONDARY_FORMAT}' \
        'secondary_device=${STB_LCD_SECONDARY_DEVICE}' \
        'secondary_width=${STB_LCD_SECONDARY_WIDTH}' \
        'secondary_height=${STB_LCD_SECONDARY_HEIGHT}' \
        'secondary_bpp=${STB_LCD_SECONDARY_BPP}' \
        'secondary_columns=${STB_LCD_SECONDARY_COLUMNS}' \
        > ${D}${sysconfdir}/stb-lcdd.conf
}

FILES:${PN} = "${bindir}/stb-lcdd ${sysconfdir}/stb-lcdd.conf"
