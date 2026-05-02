do_configure:prepend() {
    # OpenSSL 4.0 support: openssl-sys rejects OpenSSL >= 4.0.
    for d in ${CARGO_HOME}/bitbake/openssl-sys-*; do
        if [ -d "$d" ]; then
            sed -i 's/if openssl_version >= 0x4_00_00_00_0 {/if false {/' $d/build/main.rs
            pkg=$(python3 -c "import json; print(json.load(open('$d/.cargo-checksum.json')).get('package',''))")
            echo "{\"files\":{},\"package\":\"$pkg\"}" > $d/.cargo-checksum.json
        fi
    done
}
