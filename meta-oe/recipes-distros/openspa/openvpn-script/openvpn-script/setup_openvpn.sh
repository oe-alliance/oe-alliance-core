#!/bin/sh

# Script to configure openvpn server and generate client config files
#
# For more info on automating key generation,
# See http://serverfault.com/questions/172509/openvpn-easy-rsa-build-key-automation
export OPENVPN_DIR="/etc/openvpn/"
export ANDROID_DIR="${OPENVPN_DIR}/client1-android"
export IPAD_DIR="${OPENVPN_DIR}/client2-ipad"
export PC_LIN="${OPENVPN_DIR}/client3-pc_lin"


echo "Iniciando simpleconfig..."
if [ ! -d /etc/openvpn ] ; then
    mkdir -p /etc/openvpn
fi
cd /etc/openvpn
wget https://openspa.webhop.info/drivers/openvpn/openvpn-2.4.3.tar.gz
wget https://openspa.webhop.info/drivers/openvpn//configuracionopenvpn.tar.gz

echo "Extracción de archivos de instalación..."

tar -xvf openvpn-2.4.3.tar.gz
tar -xvf configuracionopenvpn.tar.gz
cp -r /etc/openvpn/openvpn-2.4.3/easy-rsa /etc/openvpn/
cd /etc/openvpn/easy-rsa/2.0/

chmod 777 *

# Change region file vars
sed -i 's/"US"/ES/g' "vars"
sed -i 's/"CA"/MA/g' "vars"
sed -i 's/"SanFrancisco"/Madrid/g' "vars"
sed -i 's/"Fort-Funston"/OPENSPA/g' "vars"

# Finally change region

# Fix create client no change
sed -i 's/export KEY_CN=changeme/#export KEY_CN=changeme/g' "vars"
sed -i 's/export KEY_NAME=changeme/#export KEY_NAME=changeme/g' "vars"
sed -i 's/export KEY_OU=changeme/#export KEY_OU=changeme/g' "vars"
sed -i 's/export PKCS11_MODULE_PATH=changeme/#export PKCS11_MODULE_PATH=changeme/g' "vars"
sed -i 's/export PKCS11_PIN=1234/#export PKCS11_PIN=1234/g' "vars"

source ./vars
./clean-all
./pkitool --initca
./pkitool --server server
./pkitool client1
./pkitool client2
./pkitool client3

./build-dh


mkdir $ANDROID_DIR
mkdir $IPAD_DIR
mkdir $PC_LIN

mv keys $OPENVPN_DIR

cp /etc/openvpn/client.ovpn  ${ANDROID_DIR}
cp /etc/openvpn/client.ovpn  ${IPAD_DIR}
cp /etc/openvpn/client.ovpn  ${PC_LIN}

cd /etc/openvpn/
echo ""
echo ""
echo ""
echo ""
echo ""
echo "Incorporación de certs y claves dentro del archivo client.ovpn para tener todo en 1"


echo -e "\n\n<ca>\n$(cat keys/ca.crt)\n</ca>" >> "${ANDROID_DIR}/client.ovpn"
echo -e "\n\n<cert>\n$(cat keys/client1.crt)\n</cert>" >> "${ANDROID_DIR}/client.ovpn"
echo -e "\n\n<key>\n$(cat keys/client1.key)\n</key>" >> "${ANDROID_DIR}/client.ovpn"

echo -e "\n\n<ca>\n$(cat keys/ca.crt)\n</ca>" >> "${IPAD_DIR}/client.ovpn"               
echo -e "\n\n<cert>\n$(cat keys/client2.crt)\n</cert>" >> "${IPAD_DIR}/client.ovpn"       
echo -e "\n\n<key>\n$(cat keys/client2.key)\n</key>" >> "${IPAD_DIR}/client.ovpn"         

echo -e "\n\n<ca>\n$(cat keys/ca.crt)\n</ca>" >> "${PC_LIN}/client.ovpn"               
echo -e "\n\n<cert>\n$(cat keys/client3.crt)\n</cert>" >> "${PC_LIN}/client.ovpn"       
echo -e "\n\n<key>\n$(cat keys/client3.key)\n</key>" >> "${PC_LIN}/client.ovpn"

rm -r openvpn-2.4.3
rm -r openvpn-2.4.3.tar.gz
rm -r easy-rsa
rm -r configuracionopenvpn.tar.gz

echo ""
echo ""
echo ""
echo "Todo Listo!"
echo "Se ha configurado el servidor y se han generado las configuraciones del cliente y están listas para transferirse desde:"
echo ${PC_LIN}
echo ${ANDROID_DIR}
echo ${IPAD_DIR}
echo ""

echo "Nota: asegúrese de iniciar automáticamente OpenVPN en la interfaz gráfica de usuario de OpenSPA!"
