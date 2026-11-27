SUMMARY = "GTX C21 factory boot resources and Windows setup kit"
require conf/license/license-close.inc
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit deploy python3native
DEPENDS += "python3-pillow-native"
SRC_URI = "${AMLOGIC_SOURCE_MIRROR}/gtxcombo-c21-assets-1.tar.xz file://make-logo.py"
SRC_URI[sha256sum] = "b5a6923454e4455c96e17067aea8cc1f6f524d17e9a6432d4cc55322ff674690"
S = "${UNPACKDIR}/gtxcombo-c21-assets-1"

do_deploy[depends] += "${DISTRO_NAME}-bootlogo:do_deploy"
do_deploy() {
    assets="${DEPLOYDIR}/gtx-c21-assets-${DISTRO_NAME}"
    install -d "$assets"
    cp -R ${S}/windows "$assets/"
    # The Windows kit ships a CRLF manifest. The filenames used by sha256sum on Linux need LF.
    tr -d '\r' < ${S}/windows/SHA256SUMS > "$assets/windows/SHA256SUMS"
    (cd "$assets/windows" && sha256sum --strict -c SHA256SUMS)
    install -m 0644 ${S}/bootloader.img "$assets/"
    # Retain the vendor update screens and replace both U-Boot startup images.
    ${PYTHON} "${UNPACKDIR}/make-logo.py" "${S}/logo.img" "${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME}" "$assets"
}
addtask deploy after do_unpack do_prepare_recipe_sysroot before do_build
