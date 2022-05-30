package com.zqh.mysystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zqh.mysystem.R;
import com.zqh.mysystem.dao.ApplicantImpl;
import com.zqh.mysystem.dao.HRImpl;
import com.zqh.mysystem.dao.MyHelper;
import com.zqh.mysystem.bean.Applicant;
import com.zqh.mysystem.bean.HR;
import com.zqh.mysystem.bean.User;
import com.zqh.mysystem.utils.Code;
import com.zqh.mysystem.utils.VerifyCode;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.activity
 * @className: MainActivity
 * @author: Zhangqihao
 * @description: index 页面
 * @date: 2022/5/21
 */
public class MainActivity extends AppCompatActivity {

    // 创建控件变量
    EditText et_input_code, et_account, et_password;
    ImageView img_code;
    TextView tv_signUp, tv_link_xieyi, tv_link_yinsi, tv_applicant, tv_hr, applicant_line, hr_line, tv_forgotPwd;
    Button btn_signIn;
    RadioButton rb_agreement;
    CheckBox ck_remPwd;

    // 全局变量
    String code; // 程序生成的验证码
    String identity = "applicant"; // 用户选择的身份(applicant：求职者，hr：HR)
    boolean agreementChecked = false; // 用户协议是否勾选(false：未勾选，true：已勾选)
    boolean remPwdChecked = false; // 记住密码复选框是否勾选(false：未勾选，true：已勾选)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); // 初始化
        new rememberPwd().resume(); // 恢复账号密码
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description 控件初始化
     * @date 2022/5/21
     */
    public void init() {
        // 获取控件
        et_account = (EditText) findViewById(R.id.et_account); // 账户输入框
        et_password = (EditText) findViewById(R.id.et_password); // 密码输入框
        et_input_code = (EditText) findViewById(R.id.et_input_code); // 验证码输入框
        img_code = (ImageView) findViewById(R.id.img_code); // 验证码图片
        btn_signIn = (Button) findViewById(R.id.btn_signIn); // 登录按钮
        rb_agreement = (RadioButton) findViewById(R.id.rb_agreement); // 协议单选框
        tv_link_xieyi = (TextView) findViewById(R.id.tv_link_xieyi); // 协议超链接
        tv_link_yinsi = (TextView) findViewById(R.id.tv_link_yinsi); // 隐私超链接
        tv_applicant = (TextView) findViewById(R.id.tv_applicant); // 求职者标签
        tv_hr = (TextView) findViewById(R.id.tv_hr); // HR标签
        applicant_line = (TextView) findViewById(R.id.applicant_line); // 求职者下划线
        hr_line = (TextView) findViewById(R.id.hr_line); // HR下划线
        tv_forgotPwd = (TextView) findViewById(R.id.tv_forgotPwd); // 忘记密码标签
        ck_remPwd = (CheckBox) findViewById(R.id.cb_remPwd); // 记住密码复选框
        tv_signUp = (TextView) findViewById(R.id.tv_signUp); // 注册标签
        tv_forgotPwd = (TextView) findViewById(R.id.tv_forgotPwd); // 忘记密码标签

        // 初始获取验证码，生成验证码图片
        img_code.setImageBitmap(Code.getInstance().createBitmap());
        code = Code.getInstance().getCode();

        // 初始化用户身份
        changeIdentity(tv_applicant);

        // 绑定监听事件
        img_code.setOnClickListener(new myOnClick());
        btn_signIn.setOnClickListener(new myOnClick());
        tv_applicant.setOnClickListener(new myOnClick());
        tv_hr.setOnClickListener(new myOnClick());
        tv_signUp.setOnClickListener(new myOnClick());
        tv_forgotPwd.setOnClickListener(new myOnClick());
        rb_agreement.setOnCheckedChangeListener(new myButtonCheckedChanged());
        ck_remPwd.setOnCheckedChangeListener(new myButtonCheckedChanged());

        // 设置超链接跳转
        tv_link_xieyi.setMovementMethod(LinkMovementMethod.getInstance());
        tv_link_yinsi.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * @param view: applicant or hr 控件
     * @return void
     * @author Zhangqihao
     * @description 切换用户身份
     * @date 2022/5/21
     */
    private void changeIdentity(View view) {
        if (view.getId() == R.id.tv_applicant) { // 如果身份为 applicant
            tv_applicant.setTextColor(Color.BLACK);
            tv_hr.setTextColor(Color.LTGRAY);
            applicant_line.setVisibility(View.VISIBLE);
            hr_line.setVisibility(View.INVISIBLE);
            identity = "applicant";
            Log.i("Login", "Sign In Identity: applicant");
        }else { // 如果身份为 hr
            tv_applicant.setTextColor(Color.LTGRAY);
            tv_hr.setTextColor(Color.BLACK);
            applicant_line.setVisibility(View.INVISIBLE);
            hr_line.setVisibility(View.VISIBLE);
            identity = "hr";
            Log.i("Login", "Sign In Identity: hr");
        }
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description 弹出注册窗口
     * @date 2022/5/22
     */
    private void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        View view = inflater.inflate(R.layout.register_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        // 显示弹窗
        dialog.show();
        Log.i("Login", "[弹出注册窗口]");

        // 获取控件
        Button btn_signUp = (Button) view.findViewById(R.id.btn_signUp);
        EditText et_account_register = (EditText) view.findViewById(R.id.et_account_register);
        EditText et_password_register = (EditText) view.findViewById(R.id.et_password_register);
        EditText et_password_confirm = (EditText) view.findViewById(R.id.et_password_confirm);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_identity);
        RadioButton radio_applicant = (RadioButton) view.findViewById(R.id.radio_applicant);
        RadioButton radio_hr = (RadioButton) view.findViewById(R.id.radio_hr);

        // 注册按钮绑定监听事件
        btn_signUp.setOnClickListener(new myDialog(dialog, et_account_register, et_password_register, et_password_confirm, btn_signUp, radioGroup));
        radioGroup.setOnCheckedChangeListener(new myRadioGroupCheckedChanged(radio_applicant, radio_hr));
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: rememberPwd
     * @author: Zhangqihao
     * @description: 实现记住密码功能
     * @date: 2022/5/21
     */
    class rememberPwd {

        private SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        private SharedPreferences.Editor spe = sp.edit();

        /**
         * @param user: user 对象
         * @return void
         * @author Zhangqihao
         * @description sharedPreferences 保存数据
         * @date 2022/5/22
         */
        void remember(User user) {
            // 添加数据
            spe.putString("account", user.getAccount());
            spe.putString("password", user.getPassword());
            spe.putString("identity", user.getIdentity());
            // 提交
            spe.commit();
            Log.i("Login", "[保存账号密码] | account:" + user.getAccount() +
                    ", password:" + user.getPassword() + ", identity:" + user.getIdentity());
        }
        /**
         * @return void
         * @author Zhangqihao
         * @description sharedPreferences 恢复数据
         * @date 2022/5/22
         */
        void resume() {
            User user = new User();
            // 恢复数据
            user.setAccount(sp.getString("account", ""));
            user.setPassword(sp.getString("password", ""));
            user.setIdentity(sp.getString("identity", ""));

            // 如果保存过账号密码
            if (!user.getPassword().equals("") && !user.getAccount().equals("")) {
                changeIdentity(user.getIdentity().equals("applicant") ? tv_applicant : tv_hr); // 自动切换用户身份
                et_account.setText(user.getAccount()); // 自动填写账号
                et_password.setText(user.getPassword()); // 自动填写密码
                ck_remPwd.setChecked(true); // 勾选记住密码
                Log.i("Login", "[获取保存账号密码] | account:" + user.getAccount() + ", password:" + user.getPassword());
            }else {
                Log.i("Login", "[未保存账号密码]");
            }
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: forgotPwd
     * @author: Zhangqihao
     * @description: // TODO:实现忘记密码功能
     * @date: 2022/5/21
     */
    class forgotPwd {
        void forgot() {
            Toast.makeText(MainActivity.this, "忘记密码你就别登了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: login
     * @author: Zhangqihao
     * @description: 实现登录功能
     * @date: 2022/5/21
     */
    class login {
        User user;
        String code_input;
        String account_input;
        String password_input;

        public login() {}
        // 密码正确
        private void success() {
            Log.i("Login", "[登陆成功] | identity:" + user.getIdentity());
            new rememberPwd().remember(user); // 存储 sharedPreferences
            Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent().setClass(MainActivity.this, HomePage.class));
        }
        // 账号或密码错误
        private void wrongAccount() {
            Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            Log.i("Login", "[账号或密码错误]");
        }
        // 账号未注册
        private void non_existent() {
            Toast.makeText(MainActivity.this, "账号未注册", Toast.LENGTH_SHORT).show();
            Log.i("Login", "[账号不存在]");
        }
        private void getData() {
            // 获取控件内容
            code_input = et_input_code.getText().toString();
            account_input = et_account.getText().toString();
            password_input = et_password.getText().toString();
            // 上转型实例化 user 对象
            user = identity.equals("applicant") ? new Applicant(account_input, password_input) : new HR(account_input, password_input);
        }

        /**
         * @return void
         * @author Zhangqihao
         * @description 尝试登录，匹配账号密码
         * @date 2022/5/21
         */
        @SuppressLint("Range")
        void tryLogin() {
            Log.i("Login", "====尝试登陆====");
            User select_user;
            int count;
            getData(); // 获取信息

            if (user.getAccount().equals("") || user.getPassword().equals("")) // 未填写账号密码
                Toast.makeText(MainActivity.this, "请填写账号密码", Toast.LENGTH_SHORT).show();
            else if (et_input_code.getText().toString().equals("")) // 未填写验证码
                VerifyCode.emptyCode(MainActivity.this);
            else if (!VerifyCode.verify(code_input)) // 验证码错误
                code = VerifyCode.wrongCode(MainActivity.this, img_code, code_input);
            else if (!agreementChecked) // 未勾选用户协议和隐私政策
                Toast.makeText(MainActivity.this, "请勾选用户协议和隐私政策", Toast.LENGTH_SHORT).show();
            else { // 填写完毕
                if (user.getIdentity().equals("applicant")) // 查询账号对应密码
                    select_user = ApplicantImpl.getInstance(new MyHelper(MainActivity.this)).Select(user);
                else
                    select_user = HRImpl.getInstance(new MyHelper(MainActivity.this)).Select(user);

                if (select_user == null) { // 不存在此账号
                    non_existent();
                    return;
                }

                if (select_user.getPassword().equals(user.getPassword())) // 比较密码
                    success(); // 密码正确
                else
                    wrongAccount(); // 密码错误
            }
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: register
     * @author: Zhangqihao
     * @description: 实现注册功能
     * @date: 2022/5/21
     */
    class register {
        User user;

        public register(User user) {
            this.user = user;
        }
        // 注册成功
        private void success() {
            Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            Log.i("Login","[注册成功] | account:" + user.getAccount() + ", password:" + user.getPassword() +
                    ", identity:" + user.getIdentity());
        }
        // 注册失败
        private void fail() {
            Toast.makeText(MainActivity.this, "此账号已注册", Toast.LENGTH_SHORT).show();
            Log.i("Login","[此账号已注册]");
        }
        // 缺少信息
        private void lostInfo() {
            Toast.makeText(MainActivity.this, "信息请输入完全", Toast.LENGTH_SHORT).show();
            Log.i("Login", "[信息未输入完全]");
        }
        // 密码不相同
        private void confirmPwd() {
            Toast.makeText(MainActivity.this, "请确保两次输入的密码相同", Toast.LENGTH_SHORT).show();
            Log.i("Login", "[两次输入密码不相同]");
        }
        private void emptyChoose() {
            Toast.makeText(MainActivity.this, "请选择注册的身份", Toast.LENGTH_SHORT).show();
            Log.i("Login", "[注册未勾选身份]");
        }


        /**
         * @param confirm_pwd: 确认的密码
         * @return boolean
         * @author Zhangqihao
         * @description 尝试注册
         * @date 2022/5/22
         */
        boolean tryRegister(String confirm_pwd) {
            boolean flag = false;
            String account = user.getAccount(), password = user.getPassword();
            Log.i("Login", "====尝试注册====");

            if (account.equals("") || password.equals("") || confirm_pwd.equals("")) // 信息未填写完全
                lostInfo();
            else if (!password.equals(confirm_pwd)) // 两次密码不相同
                confirmPwd();
            else { // 填写完毕
                if (user.getIdentity().equals("applicant")) // 勾选求职者
                    flag = new ApplicantImpl(new MyHelper(MainActivity.this)).Insert(user);
                else if (user.getIdentity().equals("hr")) // 勾选HR
                    flag = new HRImpl(new MyHelper(MainActivity.this)).Insert(user);
                else { // 未勾选身份
                    emptyChoose();
                    return false;
                }
                if (flag) // 注册成功
                    success();
                else // 账号已存在
                    fail();
            }
            return flag;
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: myOnClick
     * @author: Zhangqihao
     * @description: 自定义 myOnClick 类实现 View.OnClickListener 监听器
     * @date: 2022/5/21
     */
    class myOnClick implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_code: // 点击图片更换验证码
                    code = VerifyCode.change(img_code);
                    break;
                case R.id.tv_applicant: // 切换用户身份
                case R.id.tv_hr:
                    changeIdentity(view);
                    break;
                case R.id.tv_signUp: // 注册新用户
                    showDialog();
                    break;
                case R.id.btn_signIn: // 登录按钮
                    new login().tryLogin();
                    break;
                case R.id.tv_forgotPwd: // 忘记密码
                    new forgotPwd().forgot();
            }
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: myRadioGroupCheckedChanged
     * @author: Zhangqihao
     * @description: 自定义 myRadioGroupCheckedChanged 类实现 RadioGroup.OnCheckedChangeListener 监听器
     * @date: 2022/5/21
     */
    static class myRadioGroupCheckedChanged implements RadioGroup.OnCheckedChangeListener {
        private RadioButton radio_applicant, radio_hr;
        private static String identity= "";

        public myRadioGroupCheckedChanged(RadioButton radio_applicant, RadioButton radio_hr) {
            this.radio_applicant = radio_applicant;
            this.radio_hr = radio_hr;
        }

        /**
         * @return String
         * @author Zhangqihao
         * @description 获取注册身份
         * @date 2022/5/22
         */
        private static String getIdentity() {
            return identity;
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            identity = i == radio_hr.getId() ? "hr" : "applicant"; // 切换注册身份
            Log.i("Login", "Sign Up Identity:" + identity);
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: myButtonCheckedChanged
     * @author: Zhangqihao
     * @description: 自定义 myButtonCheckedChanged 类实现 CompoundButton.OnCheckedChangeListener 监听器
     * @date: 2022/5/21
     */
    class myButtonCheckedChanged implements CompoundButton.OnCheckedChangeListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) { // 协议单选框或复选框勾选
                switch (compoundButton.getId()) {
                    case R.id.rb_agreement:
                        agreementChecked = true;
                        Log.i("Login", "[协议已勾选]");
                        break;
                    case R.id.cb_remPwd:
                        remPwdChecked = true;
                        Log.i("Login", "[记住密码已勾选]");
                }
            }else { // 复选框取消勾选
                switch (compoundButton.getId()) {
                    case R.id.cb_remPwd:
                        remPwdChecked = false;
                        Log.i("Login", "[记住密码取消勾选]");
                }
            }
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: myDialog
     * @author: Zhangqihao
     * @description: 自定义 myDialog 类实现 View.OnClickListener 监听操作
     * @date: 2022/5/21
     */
    class myDialog implements View.OnClickListener {
        AlertDialog dialog;
        EditText et_account_register, et_password_register, et_password_confirm;
        Button btn_signUp;
        RadioGroup radioGroup;
        User user = new User();
        String ide = "";

        public myDialog(AlertDialog dialog) {
            this.dialog = dialog;
        }
        public myDialog(AlertDialog dialog, EditText et_account_register, EditText et_password_register,
                        EditText et_password_confirm, Button btn_signUp, RadioGroup radioGroup) {
            this.dialog = dialog;
            this.et_account_register = et_account_register;
            this.et_password_register = et_password_register;
            this.et_password_confirm = et_password_confirm;
            this.btn_signUp = btn_signUp;
            this.radioGroup = radioGroup;
        }

        /**
         * @return void
         * @author Zhangqihao
         * @description 获取输入注册信息
         * @date 2022/5/22
         */
        private void getData() {
            // 获取信息存入 user 对象
            user.setAccount(et_account_register.getText().toString());
            user.setPassword(et_password_register.getText().toString());
            ide = myRadioGroupCheckedChanged.getIdentity(); // 获取 radioGroup 选中内容
            user.setIdentity(ide);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            boolean flag = false;
            switch (view.getId()) {
                case R.id.btn_signUp: // 点击注册按钮
                    getData(); // 获取信息
                    flag = new register(user).tryRegister(et_password_confirm.getText().toString());
                    if (flag) { // 注册成功关闭弹窗
                        dialog.cancel();
                        Log.i("Login", "[关闭弹窗]");
                    }
                    break;

                default:
                    dialog.cancel(); // 关闭弹窗
                    Log.i("Login", "[关闭弹窗]");
            }
        }
    }
}