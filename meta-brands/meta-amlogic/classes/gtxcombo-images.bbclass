# SPDX-License-Identifier: GPL-3.0-only
# GTX C21 release formats for the full and feedless distribution images.
inherit python3native
IMAGE_FSTYPES += "tar.bz2"
# This is the main image, not the separate SmallBox companion excluded by OpenATV.
OPENATV_IMAGE_INFO_EXCLUDE:remove:openatv = "${IMAGE_NAME}_multiboot.zip"
# tar and mke2fs must see the target owners and device nodes in the staged slots.
PSEUDO_INCLUDE_PATHS .= ",${WORKDIR}/gtx-images/slots"

ROOTFS_POSTPROCESS_COMMAND:append:openatv = " gtx_remote_defaults"
gtx_remote_defaults() {
    settings="${IMAGE_ROOTFS}${sysconfdir}/enigma2/settings"
    install -d "${IMAGE_ROOTFS}${sysconfdir}/enigma2"
    if [ ! -f "$settings" ] || ! grep -q '^config\.misc\.ButtonSetup\.mouse=' "$settings"; then
        printf '\nconfig.misc.ButtonSetup.mouse=Infobar/startTeletext\n' >> "$settings"
    fi
}

# Image composition belongs here; recovery support comes from its pinned recipe.
do_gtx_images[depends] += "gtx-ramfs-image:do_image_complete gtx-bootmenu:do_deploy gtx-recovery:do_deploy gtx-c21-assets:do_deploy virtual/kernel:do_deploy \
    python3-native:do_populate_sysroot mtools-native:do_populate_sysroot dosfstools-native:do_populate_sysroot \
    util-linux-native:do_populate_sysroot e2fsprogs-native:do_populate_sysroot zip-native:do_populate_sysroot \
    unzip-native:do_populate_sysroot bzip2-native:do_populate_sysroot"
do_gtx_images[cleandirs] = "${WORKDIR}/gtx-images"
do_gtx_images[dirs] = "${WORKDIR}/gtx-images"

gtx_image_parts() {
    ${PYTHON} "${DEPLOY_DIR_IMAGE}/gtx-recovery/imageparts.py" "$@"
}

gtx_image_slots() {
    local dir=$1 uuid=$2 kernelDevice=$3 ormPart=$4 firstTitle=$5 s record extra label
    cp "$menu/bootmenu.bin" "$menu/bootmenu.scr" board/splash.bmp "$dir/gtx/"
    echo 'GTX raw kernel v1' > "$dir/gtx/raw-kernel-v1"
    printf 'default=0\ntimeout=5\n' > "$dir/bootconfig.txt"
    for s in 1 2 3 4 5 6 7 8; do
        extra=' extra=true'; label=Enigma2
        if [ "$s" = 1 ]; then extra=''; label=$firstTitle; fi
        record="root=UUID=$uuid rootsubdir=linuxrootfs$s kernel=$kernelDevice kernelpolicy=slot1 rootfstype=ext4 rw rootwait$extra"
        echo "$record" > "$dir/STARTUP_$s"
        printf '\n[%s]\ncmd=%s\n' "$label" "$record" >> "$dir/bootconfig.txt"
    done
    cp "$dir/STARTUP_1" "$dir/STARTUP"
    echo "bootcmd=orm recovery gtx.orm=$ormPart gtx.system=UUID=$uuid" > "$dir/STARTUP_RECOVERY"
}

gtx_image_fat() {
    local image=$1 size=$2 bits=$3 label=$4 dir=$5
    truncate -s "$size" "$image"
    mkfs.vfat -F "$bits" -n "$label" "$image"
    mcopy -spi "$image" "$dir"/* ::/
    fsck.fat -n "$image"
}

gtx_image_write_partition() {
    dd if="$2" of="$1" bs=1M seek="$3" conv=notrunc,sparse status=none
}

gtx_image_checksums() {
    (cd orm/gtx && sha256sum recovery.img gtx.dtb menu-assets/* install/* > SHA256SUMS)
}

gtx_image_multiboot() {
    # All three release formats use the same kernel and slot-1 root filesystem.
    gtx_image_parts kernel "$kernel" "$dtb" "$ramfs/slot.cpio.gz"
    ln kernel.bin release/gtxcombo/kernel.bin
    cp --reflink=auto "$rootTar" release/gtxcombo/rootfs.tar.bz2
    printf '%s\n' "${IMAGE_VER_STRING}" > release/gtxcombo/imageversion
    (cd release && zip -q -6 "${name}_multiboot.zip" gtxcombo/kernel.bin gtxcombo/rootfs.tar.bz2 gtxcombo/imageversion)
}

gtx_image_prepare_emmc() {
    tar --numeric-owner --xattrs -xjpf "$rootTar" -C slots/linuxrootfs1
    test -x slots/linuxrootfs1/usr/bin/enigma2
    test -x slots/linuxrootfs1/sbin/init.sysvinit
    test -x slots/linuxrootfs1/usr/sbin/gtx-hdmimode
    test -L slots/linuxrootfs1/etc/rcS.d/S41gtx-multiboot
    truncate -s 800M internal.ext4
    mkfs.ext4 -q -b 4096 -m 0 -L GTXDATA -O ^orphan_file -d slots internal.ext4
    e2fsck -fn internal.ext4
    cp --sparse=always --reflink=auto internal.ext4 usb.ext4
    tune2fs -U random -L GTX_OPENATV_USB usb.ext4
    truncate -s 1536M usb.ext4
    resize2fs usb.ext4
    e2fsck -fn usb.ext4

    internalUuid=$(blkid -p -s UUID -o value internal.ext4)
    usbUuid=$(blkid -p -s UUID -o value usb.ext4)
    if [ -z "$internalUuid" ] || [ -z "$usbUuid" ] || [ "$internalUuid" = "$usbUuid" ]; then
        bbfatal "The internal and USB root filesystems need distinct UUIDs"
    fi
    gtx_image_slots startup "$internalUuid" /dev/mmcblk0p9 8 "$title"
    gtx_image_slots usbboot "$usbUuid" /dev/sda4 3 "$title USB"
    echo "$internalUuid" > startup/gtx/grow-internal-rootfs
    echo 'GTX raw slot data v1' > usbboot/gtx/grow-slot-data
    echo 'GTX dual USB v1' > usbboot/gtx/usb-system-menu
    echo 'bootcmd=run storeboot gtx.android=1' > usbboot/STARTUP_ANDROID

    gtx_image_fat startup.fat 32M 16 GTXSTARTUP startup
    disk=compact.img
    truncate -s 1921M "$disk"
    sed 's/^start=2293760, type=/start=2293760, size=1638400, type=/' "$src/emmc-layout.sfdisk" > compact-layout.sfdisk
    sfdisk "$disk" < compact-layout.sfdisk
    # The transport root is 800 MiB; the installer extends it to the last usable LBA.
    truncate -s 1921M template.img
    sfdisk -d "$disk" > template-original.sfdisk
    sed -E '/^(device|first-lba|last-lba):/d; s/^compact\.img[0-9]+[[:space:]]*:[[:space:]]*//; /name="rootfs"/s/size=[ ]*[0-9]+/size=1640415/' template-original.sfdisk > template-layout.sfdisk
    sfdisk template.img < template-layout.sfdisk
    installDir=orm/gtx/install
    dd if=template.img of="$installDir/gpt-head.bin" bs=512 count=34 status=none
    dd if=template.img of="$installDir/gpt-tail.bin" bs=512 skip=${@1921 * 2048 - 33} count=33 status=none
    gtx_image_parts env "$installDir/env.txt"
    cp "$src/emmc-layout.sfdisk" board/bootloader.img "$installDir/"
    cp "$dtb" orm/gtx/gtx.dtb
    cp "$menu/bootmenu.bin" "$menu/bootmenu.scr" orm/gtx/menu-assets/
    echo 'GTX ORM 1' > orm/gtx/orm-slot
    gtx_image_parts orm "$ramfs/orm.cpio.gz" orm/gtx
    gtx_image_checksums
    gtx_image_fat internal-orm.fat 64M 32 GTXORM orm

    gtx_image_write_partition "$disk" startup.fat 4
    gtx_image_write_partition "$disk" board/logo.img 644
    gtx_image_write_partition "$disk" internal-orm.fat 960
    gtx_image_write_partition "$disk" kernel.bin 1056
    gtx_image_write_partition "$disk" internal.ext4 1120
    sfdisk --verify "$disk"
    gtx_image_parts plan "$disk" "$installDir" install-plan.json
    zip -q -6 openatv-gtxcombo-emmc-install.zip "$disk"
    gtx_image_parts orm "$ramfs/orm.cpio.gz" orm/gtx install-plan.json
    gtx_image_checksums
    gtx_image_fat usb-orm.fat 36M 16 GTXORM orm
}

gtx_image_recovery() {
    mkdir standalone
    cp -r board/windows/. standalone/
    (cd standalone && sha256sum --strict -c SHA256SUMS)
    cp -r orm/gtx standalone/
    rm standalone/gtx/orm-slot
    cp "$src/EMMC-INSTALL.txt" standalone/
    gtx_image_fat rescue.fat 512M 32 GTXRESCUE standalone
    truncate -s 513M recovery.img
    printf 'label: dos\nlabel-id: 0x58545247\nunit: sectors\n\nstart=2048, size=1048576, type=c, bootable\n' | sfdisk recovery.img
    gtx_image_write_partition recovery.img rescue.fat 1
    sfdisk --verify recovery.img
    zip -q -6 "release/$name-recovery.zip" recovery.img
}

gtx_image_usb_emmc() {
    cp -r board/windows/. usbboot/
    sed -i -e 's/GTXRESCUE/GTXBOOT/g' -e 's/<b>recovery.img<\/b>/the extracted <b>Enigma2 USB IMG<\/b>/g' \
        -e 's@Look for <b>Open Recovery Manager (ORM)</b> at the top left of the TV screen.@Choose the USB image in slot 1, or USB Recovery for maintenance.@g' usbboot/START-HERE.html
    (cd usbboot && awk '{print $2}' SHA256SUMS | xargs sha256sum > sums.tmp && mv sums.tmp SHA256SUMS)
    truncate -s 412M usbboot.fat
    mkfs.vfat -F 32 -n GTXBOOT usbboot.fat
    # Write the installer first, before any other FAT allocation.
    mcopy -i usbboot.fat openatv-gtxcombo-emmc-install.zip ::/
    mcopy -spi usbboot.fat usbboot/* ::/
    fsck.fat -n usbboot.fat
    available=$(mdir -i usbboot.fat ::/ | awk '/bytes free/ {gsub(/[^0-9]/,""); print}')
    case "$available" in
        ''|*[!0-9]*) bbfatal "Could not read free space in the GTX boot partition" ;;
    esac
    [ "$available" -gt ${@144 * 1048576 + 65536} ] || bbfatal "Insufficient FAT space for per-receiver backups"

    usb="$name-usb-emmc.img"
    truncate -s 2049M "$usb"
    sfdisk "$usb" <<'MBR'
label: dos
unit: sectors

start=2048, size=843776, type=c, bootable
start=1050624, size=3145728, type=83
start=845824, size=73728, type=e
start=919552, size=131072, type=83
MBR
    gtx_image_write_partition "$usb" usbboot.fat 1
    gtx_image_write_partition "$usb" usb-orm.fat 413
    gtx_image_write_partition "$usb" kernel.bin 449
    gtx_image_write_partition "$usb" usb.ext4 513
    gtx_image_parts marker "$usb" openatv-gtxcombo-emmc-install.zip
    sfdisk --verify "$usb"
    zip -q -6 "release/$name-usb-emmc.zip" "$usb"
}

fakeroot do_gtx_images() {
    export LC_ALL=C MTOOLS_SKIP_CHECK=1 PYTHONDONTWRITEBYTECODE=1
    src="${DEPLOY_DIR_IMAGE}/gtx-recovery"
    rootTar="${IMGDEPLOYDIR}/${IMAGE_NAME}.tar.bz2"
    kernel="${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}"
    dtb="${DEPLOY_DIR_IMAGE}/${@os.path.basename(d.getVar('KERNEL_DEVICETREE'))}"
    ramfs="$PWD/ramfs"
    menu="${DEPLOY_DIR_IMAGE}/gtx-bootmenu"
    assets="${DEPLOY_DIR_IMAGE}/gtx-c21-assets-${DISTRO_NAME}"
    output="${IMGDEPLOYDIR}"
    name="${IMAGE_NAME}"
    title="${DISTRO_NAME} ${IMAGE_VERSION}"

    mkdir ramfs
    tar -xf "${DEPLOY_DIR_IMAGE}/gtx-ramfs-${MACHINE}.gtxramfs" -C ramfs
    for slot in 1 2 3 4 5 6 7 8; do
        mkdir -p "slots/linuxrootfs$slot"
    done
    mkdir -p startup/gtx usbboot/gtx orm/gtx/install orm/gtx/menu-assets release/gtxcombo
    ln -s "$assets" board

    gtx_image_multiboot
    gtx_image_prepare_emmc
    gtx_image_recovery
    gtx_image_usb_emmc

    install -m 0644 release/*.zip "$output/"
}
addtask gtx_images after do_image_tar before do_image_complete

# Run after the distribution's information hook, before deploying the archives.
gtx_image_finalize() {
    cd "${IMGDEPLOYDIR}"
    for file in "${IMAGE_NAME}_multiboot.zip" "${IMAGE_NAME}-recovery.zip" "${IMAGE_NAME}-usb-emmc.zip"; do
        unzip -tq "$file"
    done
    sha256sum "${IMAGE_NAME}_multiboot.zip" "${IMAGE_NAME}-recovery.zip" "${IMAGE_NAME}-usb-emmc.zip" > "${IMAGE_NAME}.sha256"
}
IMAGE_POSTPROCESS_COMMAND:append = " gtx_image_finalize"
