package com.github.esrrhs.fakescript;

public class fake
{
	protected boolean error = false;
	protected String errorstr = "";
	protected callback cb = null;

	// 配置
	protected fkconfig cfg = new fkconfig();

	// 解析
	protected parser pa = new parser(this);

	// 参数栈
	protected paramstack ps = new paramstack(this);

	// 二进制
	protected binary bin = new binary(this);

	// 函数索引
	protected funcmap fm = new funcmap(this);

	// 性能检测
	protected profile pf = new profile(this);

	// 内建的函数集合
	protected buildinfunc bif = new buildinfunc(this);

	// 当前运行状态
	protected running rn = new running(this);

	// debug容器
	protected debuging dbg = new debuging(this);

	// 优化
	protected optimizer opt = new optimizer(this);

	protected fake clonef()
	{
		fake nf = new fake();

		nf.cfg = this.cfg;
		nf.cb = this.cb;
		nf.pa = this.pa.clonef(this);
		nf.ps = new paramstack(this);
		nf.bin = new binary(this);
		nf.fm = this.fm.clonef(this);
		nf.pf = new profile(this);
		nf.bif = new buildinfunc(this);
		nf.rn = new running(this);
		nf.dbg = new debuging(this);
		nf.opt = new optimizer(this);

		return nf;
	}

	public void clearerr()
	{
		error = false;
		errorstr = "";
	}
}
