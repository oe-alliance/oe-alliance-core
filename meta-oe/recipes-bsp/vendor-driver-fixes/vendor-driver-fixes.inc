# Patch only freshly installed vendor files; keep downloads and ${S} untouched.
inherit python3native

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://patch_dvb_init.py \
    file://patch_dvb_acm.py \
    file://patch_dvb_gamma.py \
    file://patch_nexus_hlg.py \
    file://patch_dvb_bcm7444_gamma.py \
"

vendor_driver_apply_patch() {
    patch_script="$1"
    shift
    ${PYTHON} "${UNPACKDIR}/$patch_script" "$@" \
        || bbfatal "Vendor driver fix $patch_script failed for ${PN}; refusing to package unpatched drivers"
}

do_install:append() {
    case "${VENDOR_DRIVER_FIX_PROFILE}" in
        platform)
            platform_dir="${D}/home/root/platform"
            bbnote "Vendor driver hashes before fixes (${PN})"
            sha256sum "$platform_dir/dvb_init" "$platform_dir/dvb.ko" "$platform_dir/nexus.ko"
            vendor_driver_apply_patch patch_dvb_init.py "$platform_dir/dvb_init"
            vendor_driver_apply_patch patch_dvb_acm.py "$platform_dir/dvb.ko"
            vendor_driver_apply_patch patch_dvb_gamma.py "$platform_dir/dvb.ko"
            vendor_driver_apply_patch patch_nexus_hlg.py "$platform_dir/nexus.ko" -o "$platform_dir/nexus.ko"
            bbnote "Vendor driver hashes after fixes (${PN})"
            sha256sum "$platform_dir/dvb_init" "$platform_dir/dvb.ko" "$platform_dir/nexus.ko"
            ;;
        ultimo)
            driver_file="${D}/lib/modules/${KV}/extra/dvb-bcm7444.ko"
            bbnote "Vendor driver hash before fixes (${PN})"
            sha256sum "$driver_file"
            vendor_driver_apply_patch patch_dvb_bcm7444_gamma.py "$driver_file"
            bbnote "Vendor driver hash after fixes (${PN})"
            sha256sum "$driver_file"
            ;;
        *)
            bbfatal "Unsupported vendor driver fix profile: ${VENDOR_DRIVER_FIX_PROFILE}"
            ;;
    esac
}
