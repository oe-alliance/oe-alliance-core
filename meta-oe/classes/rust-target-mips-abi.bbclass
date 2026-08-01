# rustc 1.96+ rejects MIPS target JSONs that lack the "abi" field with:
#   error: error loading target specification: invalid MIPS ABI name and
#          `cfg(target_abi)` combination
# OE-core's rust-target-config.bbclass sets "abi" only for arm/armv7.
# This post-hook injects "abi": "o32" into JSONs whose arch == "mips",
# leaving all other target specs untouched. Runs only for recipes that
# have the do_rust_gen_targets task (i.e. rust recipes), so it is safe
# to INHERIT globally.
#
# Drop this class once OE-core carries the equivalent fix. Upstream
# patch submitted to openembedded-core@lists.openembedded.org:
#   https://lists.openembedded.org/g/openembedded-core/message/242496

python fix_rust_mips_abi () {
    import json, os
    wd = d.getVar('RUST_TARGETS_DIR')
    if not wd or not os.path.isdir(wd):
        return
    for fname in os.listdir(wd):
        if not fname.endswith('.json'):
            continue
        path = os.path.join(wd, fname)
        with open(path) as f:
            spec = json.load(f)
        if spec.get('arch') == 'mips':
            changed = False
            if 'abi' not in spec:
                spec['abi'] = 'o32'
                changed = True
            # rustc >= 1.96 (PR rust-lang/rust#153769) also requires
            # llvm-abiname to be set, else "invalid MIPS ABI name" fires
            # even when "abi" is present.
            if 'llvm-abiname' not in spec:
                spec['llvm-abiname'] = 'o32'
                changed = True
            if changed:
                with open(path, 'w') as f:
                    json.dump(spec, f, indent=4)
}

do_rust_gen_targets[postfuncs] += "fix_rust_mips_abi"
