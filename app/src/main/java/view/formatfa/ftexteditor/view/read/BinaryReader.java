package view.formatfa.ftexteditor.view.read;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryReader {

    public class StringOffset {
        private int offset;
        private int len;
        private String data;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLen() {
            return len;
        }

        public void setLen(int len) {
            this.len = len;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }


    }

    private InputStream stream;

    int lessLength = 3;

    public BinaryReader(InputStream stream) {
        super();
        this.stream = stream;
    }

    private byte[] buff;
    private List<StringOffset> offsets;


    public List<StringOffset> getOffsets() {
        return offsets;
    }

    public List<String> getStrings() {
        if (offsets == null) return null;

        List<String> result = new ArrayList<String>();
        for (StringOffset set : offsets)
            result.add(set.getData());
        return result;
    }

    public void writeString(List<String> res) throws Exception {
        if (res.size() != offsets.size()) throw new Exception("length not equals");
        int i = 0;
        for (StringOffset set : offsets) {
            if (res.get(i).length() != set.getLen()) {
                throw new Exception("length not equals in :" + i);
            }
            set.setData(res.get(i));
            i += 1;
        }
    }


    public void setStrings(List<String> strs) {
        for (int i = 0; i < strs.size(); i += 1) {
            offsets.get(i).setData(strs.get(i));
        }

    }

    public void setOffsets(List<StringOffset> offsets) {
        this.offsets = offsets;
    }

    byte[] getByte(int start, int len) {
        byte[] result = new byte[len];
        System.arraycopy(buff, start, result, 0, len);
        return result;

    }

    public void write(OutputStream os) throws IOException {
        System.out.println("write " + offsets.size());
        int preOffset = 0;
        int p = 0;
        for (StringOffset set : offsets) {

            int rawLen = set.getOffset() - preOffset;
            if (rawLen > 0) {

                os.write(getByte(preOffset, rawLen));
            }

            os.write(set.getData().getBytes());

            p += 1;

            preOffset = set.getOffset() + set.getLen();

        }

        System.out.println("last :" + preOffset);
        os.write(getByte(preOffset, buff.length - preOffset));

        os.flush();

    }

    public void read() throws IOException {
        offsets = new ArrayList<StringOffset>();

        buff = new byte[stream.available()];
        stream.read(buff);
        System.out.println("Tes");
        ArrayList<Byte> temp = new ArrayList<Byte>();
        System.out.println("" + buff.length);

        for (int i = 0; i <= buff.length; i += 1) {
            if (i == buff.length || buff[i] == 0) {
                byte[] bs = new byte[temp.size()];
                for (int j = 0; j < temp.size(); j += 1) bs[j] = temp.get(j);
                String str = new String(bs);

                if (Arrays.equals(bs, str.getBytes())) {
                    if (temp.size() >= lessLength) {
                        StringOffset offset = new StringOffset();
                        offset.setData(str);
                        offset.setOffset(i - bs.length);
                        offset.setLen(bs.length);
                        offsets.add(offset);


                    }
                }
                temp = new ArrayList<Byte>();
                continue;
            }

            temp.add(buff[i]);


        }
        System.out.println("finish");


    }


}
