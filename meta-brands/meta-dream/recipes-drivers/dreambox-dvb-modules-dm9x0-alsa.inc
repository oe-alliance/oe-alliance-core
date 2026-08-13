FILESEXTRAPATHS:prepend := "${THISDIR}/dreambox-dvb-modules:"

SRC_URI:append = " file://patch_dm9x0_bcm7439_alsa_open.py"

# The vendor module carries two independent ALSA defects: its playback-open
# callback reads an unset substream private pointer and its playback-ack path
# can dereference an SFIFO pointer after a stop/start race.  Patch the closed
# binary deterministically and fail the build if the result is not exactly the
# module verified for the selected Dreambox.
PR:append = ".alsa1"

DM9X0_ALSA_PATCH_SHA256:dm900 = "1810357701c58a45ca9e68b55704b5564e5f659a77d8c72552b560741d2cde8c"
DM9X0_ALSA_PATCH_SHA256:dm920 = "461af683fcdf79777c4db0e95cd0065d3872950727ba26b837e413084f26bd5f"

python do_dm9x0_alsa_patch() {
    import hashlib
    import importlib.util
    import pathlib
    import shutil

    module = pathlib.Path(d.expand(
        "${D}/lib/modules/${DM_LOCALVERSION}/extra/bcm7439.ko"
    ))
    patched = pathlib.Path(d.expand("${T}/bcm7439.ko.alsa-fixed"))
    script = pathlib.Path(d.expand(
        "${UNPACKDIR}/patch_dm9x0_bcm7439_alsa_open.py"
    ))

    spec = importlib.util.spec_from_file_location("dm9x0_alsa_patcher", script)
    patcher = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(patcher)
    patcher.patch_driver(module, patched)

    digest = hashlib.sha256(patched.read_bytes()).hexdigest()
    expected = d.getVar("DM9X0_ALSA_PATCH_SHA256")
    if digest != expected:
        bb.fatal(
            "patched bcm7439.ko SHA256 mismatch: got %s, expected %s"
            % (digest, expected)
        )

    shutil.copyfile(patched, module)
    module.chmod(0o644)
}

addtask dm9x0_alsa_patch after do_install before do_package
