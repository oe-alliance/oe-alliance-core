E2CORE_FEATURES ?= ""

def get_transcoding_plugin_package(d):
    machine_features = set((d.getVar("MACHINE_FEATURES") or "").split())
    e2core_features = set((d.getVar("E2CORE_FEATURES") or "").split())

    has_old_transcoding = "transcoding" in machine_features
    has_old_multitranscoding = "multitranscoding" in machine_features

    has_new_transcoding_support = bool(
        machine_features.intersection({
            "transcoding",
            "multitranscoding",
            "encoder",
        })
    )

    new_e2_core = "transcodingsettings" in e2core_features

    if new_e2_core:
        if has_new_transcoding_support:
            return "enigma2-plugin-systemplugins-transcodingsettings"
        return ""

    pkgs = []

    if has_old_transcoding:
        pkgs.append("enigma2-plugin-systemplugins-transcodingsetup")

    if has_old_multitranscoding:
        pkgs.append("enigma2-plugin-systemplugins-multitranscodingsetup")

    return " ".join(pkgs)