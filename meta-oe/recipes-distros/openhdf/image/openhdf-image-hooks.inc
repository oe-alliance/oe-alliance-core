# OpenHDF image hooks
MAINTAINER = "openHDF Team"

image_combine_spdx() {
}

PR = "r${DATE}"
PR[vardepsexclude] += "DATE"
REPRODUCIBLE_TIMESTAMP_ROOTFS = "${@time.strftime('%s')}"

fakeroot rootfs_postprocess() {
    if [ -f ~/bin/parser.sh ]; then
        cp ~/bin/parser.sh .
        ./parser.sh ${MACHINEBUILD} ${IMAGE_ROOTFS}
        rm -rf parser.sh
    fi
    cd ${IMAGE_ROOTFS}
    ln -s usr/share/enigma2/spinner usr/share/enigma2/skin_default/spinner || true

    echo ${DEPLOY_DIR_IMAGE} > /tmp/DEPLOY_DIR_IMAGE
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "
do_rootfs[network] = "1"
