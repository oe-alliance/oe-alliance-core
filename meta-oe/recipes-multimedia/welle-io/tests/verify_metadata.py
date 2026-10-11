"""Replay ETI FIBs through welle's real parser and test the actual E2 formatter.
Usage: python verify_metadata.py /path/to/recipe-patched-welle /path/to/ert.eti
Requires a host C++14 compiler; no RTL hardware or audio libraries are needed.
"""
from pathlib import Path
import subprocess
import sys
import tempfile

root, capture = map(lambda value: Path(value).resolve(), sys.argv[1:])
source = (root / "src/welle-e2/welle-e2.cpp").read_text()
formatter = source.split("void announceSelectedService(", 1)[1].split("} // namespace", 1)[0]
formatter = "void announceSelectedService(" + formatter
harness = r'''
#include "backend/fib-processor.h"
#include "libs/json.hpp"
#include <array>
#include <cassert>
#include <fstream>
#include <iostream>
using nlohmann::json;
class Controller : public RadioControllerInterface {
public:
 void onSNR(float) override {}
 void onFrequencyCorrectorChange(int,int) override {}
 void onSyncChange(char) override {}
 void onSignalPresence(bool) override {}
 void onServiceDetected(uint32_t) override {}
 void onNewEnsemble(uint16_t) override {}
 void onSetEnsembleLabel(DabLabel&) override {}
 void onDateTimeUpdate(const dab_date_time_t&) override {}
 void onFIBDecodeSuccess(bool,const uint8_t*) override {}
 void onNewImpulseResponse(std::vector<float>&&) override {}
 void onConstellationPoints(std::vector<DSPCOMPLEX>&&) override {}
 void onNewNullSymbol(std::vector<DSPCOMPLEX>&&) override {}
 void onTIIMeasurement(tii_measurement_t&&) override {}
 void onMessage(message_level_t,const std::string&,const std::string&) override {}
};
// The formatter uses only these two receiver accessors; both use the real FIB parser.
class RadioReceiver {
public:
 Controller controller;
 FIBProcessor fib{controller};
 std::list<ServiceComponent> getComponents(const Service& service) const { return fib.getComponents(service); }
 Subchannel getSubchannel(const ServiceComponent& component) const { return fib.getSubchannel(component); }
};
static std::vector<json> messages;
static void emitMetadata(const json& value) { messages.push_back(value); }
static std::string trimLabel(const std::string& value) { return value; }
'''
harness += formatter
harness += r'''
static uint16_t crc(const uint8_t *data, size_t length) {
 uint16_t value=0xffff;
 for(size_t i=0;i<length;++i) {
  value ^= uint16_t(data[i])<<8;
  for(int j=0;j<8;++j) value=(value<<1)^((value&0x8000)?0x1021:0);
 }
 return value^0xffff;
}
int main(int argc, char **argv) {
 assert(argc==2);
 RadioReceiver receiver;
 std::ifstream input(argv[1],std::ios::binary);
 assert(input.good());
 std::array<uint8_t,6144> frame;
 size_t fibs=0;
 while(input.read(reinterpret_cast<char*>(frame.data()),frame.size())) {
  const size_t start=12+4*(frame[5]&127);
  const size_t length=((frame[6]>>3)&3)==3?128:96;
  for(size_t offset=start;offset<start+length;offset+=32) {
   assert(crc(frame.data()+offset,30)==((frame[offset+30]<<8)|frame[offset+31]));
   std::array<uint8_t,256> bits;
   for(size_t i=0;i<256;++i) bits[i]=(frame[offset+i/8]>>(7-i%8))&1;
   receiver.fib.processFIB(bits.data(),fibs++);
  }
 }
 assert(fibs==22461);
 size_t tested=0;
 for(auto service:receiver.fib.getServiceList()) {
  if(service.serviceId<0x1201 || (service.serviceId>0x120d && service.serviceId!=0x1332)) continue;
  ++tested;
  std::string last;
  messages.clear();
  announceSelectedService(receiver,service,last);
  assert(messages.size()==2);
  const auto metadata=messages.back();
  assert(metadata["language"]=="112");
  assert(metadata["protectionLevel"]==((service.serviceId==0x1206||service.serviceId==0x1332)?"EEP 3-A":"EEP 2-A"));
  announceSelectedService(receiver,service,last);
  assert(messages.size()==2); // unchanged metadata is not resent
  service.language=9; // optional service language must not override Greek audio subchannel
  announceSelectedService(receiver,service,last);
  assert(messages.size()==2);
  // Simulate a first announcement before FIC language has arrived, then refresh.
  last="{\"language\":\"Unknown\"}";
  announceSelectedService(receiver,service,last);
  assert(messages.size()==4 && messages.back()["language"]=="112");
  std::cout<<std::hex<<service.serviceId<<std::dec<<" "<<metadata.dump()<<"\n";
 }
 assert(tested==14);
 std::cout<<"RTL metadata tests passed: 14 services, 22461 valid FIBs\n";
}
'''
with tempfile.TemporaryDirectory() as directory:
    path = Path(directory)
    (path / "test.cpp").write_text(harness)
    cmd = ["g++", "-std=c++14", "-O1", "-g", "-fsanitize=address,undefined"]
    cmd += [f"-I{root / 'src'}", f"-I{root / 'src/backend'}", f"-I{root / 'src/various'}"]
    cmd += [str(path / "test.cpp")]
    cmd += [str(root / f"src/backend/{name}.cpp") for name in ["fib-processor", "dab-constants", "charsets"]]
    cmd += ["-o", str(path / "test")]
    subprocess.run(cmd, check=True)
    import os
    env = dict(os.environ, ASAN_OPTIONS="detect_leaks=0")
    result = subprocess.run([str(path / "test"), str(capture)], capture_output=True, text=True, env=env)
    if result.returncode:
        print(result.stderr[-5000:])
        raise SystemExit(result.returncode)
    print(result.stdout)
