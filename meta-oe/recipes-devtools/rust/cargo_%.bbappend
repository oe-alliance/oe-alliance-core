do_configure:prepend() {
    # OpenSSL 4.0 support: openssl-sys 0.9.111 rejects OpenSSL >= 4.0.
    for d in ${RUSTSRC}/vendor/openssl-sys-*; do
        sed -i 's/if openssl_version >= 0x4_00_00_00_0 {/if false {/' $d/build/main.rs
        pkg=$(python3 -c "import json; print(json.load(open('$d/.cargo-checksum.json')).get('package',''))")
        echo "{\"files\":{},\"package\":\"$pkg\"}" > $d/.cargo-checksum.json
    done

    # OpenSSL 4.0: fix opaque ASN1 types in vendored curl
    for d in ${RUSTSRC}/vendor/curl-sys-*/curl/lib/vtls/openssl.c; do
        if [ -f "$d" ]; then
            sed -i 's/num->type == V_ASN1_NEG_INTEGER/ASN1_STRING_type(num) == V_ASN1_NEG_INTEGER/' "$d"
            sed -i 's/num->length/ASN1_STRING_length(num)/g' "$d"
            sed -i 's/num->data/ASN1_STRING_get0_data(num)/g' "$d"
            sed -i 's/psig->length/ASN1_STRING_length(psig)/g' "$d"
            sed -i 's/psig->data/ASN1_STRING_get0_data(psig)/g' "$d"
        fi
    done
    for d in ${RUSTSRC}/vendor/curl-sys-*; do
        if [ -d "$d" ]; then
            pkg=$(python3 -c "import json; print(json.load(open('$d/.cargo-checksum.json')).get('package',''))")
            echo "{\"files\":{},\"package\":\"$pkg\"}" > $d/.cargo-checksum.json
        fi
    done
}
