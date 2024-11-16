package com.example.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Util {

    public static String pidData = "<PidData><Data type=\"X\">MjAyMC0wOS0xMFQxNTo1NDozMqPXOJ6d8h15QrwaCZpIGjHt2XL1AlzuyleFyMV5KLKff1yYdqSu\n" +
            "6JpxFjX50Imgq+YH+v/gWttbQz2bFM+h+rB92edobjyPV6SmWTt9o2cQwdnU5/5JprWrdLkmR+r2\n" +
            "ryZiXL0Ylw+QCjBZcXjxEfDJSBDrdFtjXLd2b1/7nW17ySPt6QWhlR60CiOXR6Q6eQ7HTfkm9Gnj\n" +
            "Dc8gGC9Y9v95Ou/M6XQB4xA0pluOXcl0WbBJbLEX5YyHq3wXBnng6WEaJwFKPwAV6X0CmLRIRioP\n" +
            "XH3Y1lsCqjtZ66NViTVtAP2VD9OUUlffiZryTkIR2CAoFkchfe0uBHEYej3Lnk37GGTxCYssDLAM\n" +
            "q25bHeR4XvHBYnrKI+Xqk0SW/3mzRpOttNXOw2cOEFm0Vur9PSnRVp3uiJY48RWqjTlBBgbWxD1g\n" +
            "RkOH1KUC6iMoRHbz7k8a3ms3zQ2bei9Nz1ZU5AHkwSRH0bze0QXQgTqB79LUzPKgHnJ5OfYPre6o\n" +
            "HP7LNXzeBkYc37eIvtagZudhNgjI9nyPEXlXSXQPW7AHzoeN+1PiJfnvI+sq8kExcQn2BBFgboh4\n" +
            "6wyiOV4I/PAM80Ra/7npUR+E2vivTd1ABlTXEWMnpRSBr4LVKsf+I3OKZvp9zgNT2xzATtlZfbgb\n" +
            "MHq/JqlcunQx9S1gUClHSAwSKoVAv0FlkaxT3Y+tLG4/qf+aOC6O3G8YRbPFqEeZSi/af8TNua5Z\n" +
            "K7Vt+EFxMl9uHkYtvHU570WZgKJruE7Hc47voOfaAJ/EkKqwMUXLaD5U2xq5eGrMzLkD/gpqSqva\n" +
            "OHgtE6RZl9mrCmoQA372oH3CgubypB/wKXrGSlv6WAAK4xwuuISbWlVEHyKfW/a/PdPgV2xJusHd\n" +
            "OK/Ac27bjV8tqbkmSu5ZA8CuSiY8KhbWjWIfP4A9NRsIiFQlAVDgpe+3fYnmL0W9xWa6Fzr9KCpx\n" +
            "l8Wgz1LMU/qOx8w1pFGWpO0iiRWkpgV8i7gTO4tu6UdfSIvIZYkmYe4zmlR38cKb+qn2o+kWBdd7\n" +
            "qYzh++7cT+Gwzzuj1n2jzH+O3OyWDGaONVjr42Uj+ja7HfATg4IhcoVPnh81wZzFV81F+NgRwMZG\n" +
            "B0QfPQKpjqB2721D9BSDcLYabqHNY96eibjXu6VEI48y/z1wVFNJdFaie3x7G3DsDSVW4iXHnKr1\n" +
            "VpODyde1wmi796ITAngml16kyabd5Bgvjcreb9RsZSfPoyxrzjNemR+5N83DJcElT3BWwTaxHMbr\n" +
            "SwEHT9CUa7WDjtW4VxAjXu6/mmre5hyGcyD391psslMlDwNy8ve8z8f0TM6ItRqPk9vPpn1i7hsH\n" +
            "DYghdLmnmOWoQ4klSUn3FdR7bNOVsyHpTFe0vYXSsvfJYDvXnBAV6UTXQR7JJO83ogvJOeADK7EH\n" +
            "jvduhLT/xbZSjr0vL1cbD56wM0kovjNtnxHGvKzKAfHfCWGdUadsUJOvFL2SEeWjfagXWd2dODV3\n" +
            "JPGMLuiU1hICyh7QGTVecK5fq1cqLXojBnAKcH61l15e1LLlSbzXNsHSxHOx5TyAqSo6+XVP6goB\n" +
            "hLNx3p2JGj9u9xxIf47c393RENuhALOBL2hToo02x3cSNL9vMFH1lBvWwP6yxQeEXIVCIqCHXeET\n" +
            "OGl+NgWs9wErhrYA5mZg5/+53c87Z+BzOKsos7HqTUWtdJmsIOxJ7odsAAcV9oP/bNx7ObNKQWuB\n" +
            "ZP6UkFkW+NrMHK6y7Kp+/hcguVIS0nQsnTnzlqkPAwALGRQ2GHFvn1dYf6Ubrs2Lnui2uEyaHhvb\n" +
            "pUo2TTETwt4dbs2cB4LhzelV9A/Pdjz+jbHKwC/JmszFQmdA3hz/W/XEWyMHFKM05m+tiYol6d0f\n" +
            "pjItWPpPk3x1d8frGKq1sTsQhEpanFcEOk+lLikwOk08Y4dBifwNtRh4jI2cyAZ/GYC4D8GzS3dg\n" +
            "E6BYL1Vd+QKZVISMBhXyMwG/EyewpJtYVUFreMQfRgLtfHc3eWFc3miD9F68UHHkVSkv4dMyXco1\n" +
            "82z0KQWr2IYxfMMEtkTbFddBqs+ydLTRQlZEenrztJvo9+LFT/KH3RsCIt6drLni8Or7We3L6zgb\n" +
            "7fmOtjhO5PT/x9WugvoO3XlQvNUw6esMtwJ0IGIrtOdTVMew9ieCNHYu7w9mQZmZZ1g1JSK8mAfn\n" +
            "AYKcyxIZ3erP/IxbFXmwM84mSyEZXRk2TnKPhfY5La+k+z3YTAU0ISJCfZCKcuceR96BotmAsr9z\n" +
            "WA75ncDyFeK3YHaccdGdx4iWAb+l7eR6jHMeawc0UMQRSvfyK92XQ+WbKU8X9jZBFCkXd0VclMN4\n" +
            "RZeIRisv9RxRWE3ugPsat0F36iLAyoTCKZIQ56sOhaUN9bb/dghJhmvp3ZfqhOUUi7PUXMNleLYG\n" +
            "UKuf7pUCWM08Lr/H2hoXFU888j2Utrm0lbxrjtW/r7S174vBGf9QAHBoar6BnsMQ+LFsY5dF7P2e\n" +
            "6/PkvxewCnaUWzli822Wz49hp82wR1FmgPyqPyT0yzJSUcLBeHDfZKNHWXZz5RJVhs18jlnAv7+Q\n" +
            "BqKfyRoBeic4VXm8cDG9S7jBmlx0mWVq6Ka058SOWyoyKiYuKpY8AalH5WwUaTeKmRJvie1YWVXV\n" +
            "45QmFF8sjGxcCeHvz9MhCTUHDf9YJ7QfHzE6TVznyEYTWeGYE3F7f7r3WbuWpCtDK7sNHyNyzL8+\n" +
            "1+DD+rrXmob2cnQemE4zhwxL9e2Q40rQmlFSLZxCwGlJrMsPHPrFPsQBb02o8lTj2yictvJe4cx/\n" +
            "97N73YH+ZXx36nDu0luLUzHyNHhI2CLo3Jgo1seeZ2jORWqCkvJLl4D4DtK3cZs7IH3WHZffzzoE\n" +
            "wwXiWExx6pePYSY19LGD72sZCJU+t7Bfura/1x0Ckxbig/+zX9wiBmNZccj7He7R+h3lmWiTNgwe\n" +
            "Om0ZyNLwyNYEGbbw0z52mYSQZ/jcMMBRHMsyi+A8pH8DNbuOiU6pZp1YaOgkzfb6JB8fTol1tm4k\n" +
            "Pf/m3cWJan5xX2NR4XUQ6JUOlxsY2+Rzi5Xk7TZ3Y5Jeqw7QnepLck+jh4b2N0FWTV/+GdqZHTrK\n" +
            "JYfvSyeMTgCjzuSwAqrphZ2uSc5G21wQMEbNmsvYg8+aqxJhbH3HXSIOq6EHgHDO+7YYbGIwxuZu\n" +
            "jh6OYlqld5HHvmaw/+PkaJKnbHbJH/b9+rEzV93wRHpVKsbO1hJCVG2mP5ljlGASTxgwBpVuuvpy\n" +
            "EGEV2n+1Uw+TwoFRfFqD5Mlvc2/J0D0BKLyYC21GtzbSGr3OdPSZMMb5dPqvIt5LO6MmQE9eYc8Z\n" +
            "6X8Vo1OItwlR3oVmYAQvq5qOIAm9pyiB2/Ht6QXhMWuBlJhXQfOcveoboxPLoTXE8uXMDmE70aTb\n" +
            "2OToOgykk10Dir4TMQCVyBK6UxP1VIvnAFARZR4GKTPQcTMQp1LfCqRmna9IzTDoV+4Vnvu+VfXh\n" +
            "OGHCiJWFXXJn9bt3FCVQS4ugSPzUZUEsHfQeWzpjzAOv84xYYnPfBUULSjaBGvp+YP8U4hgA8DSi\n" +
            "7LGO2C6C4I+BSOuB9F56mBI7F9nl6JliOV76vSKaQczuCJaZPcALXh2eI1gWz5APh/97uEfsyIFp\n" +
            "ID/qifbE3v3G3LohAB/gIrWLaIhxDpGQs4DjRHVqL2f42mR2JB0RwqRwIf4UsDWYiuBKbSanj9B4\n" +
            "9x8W8y/r0NJgUBNIdnQ7wEkZkdWPtYqZIutJlQuEixwlUfzwk5saNuATqUVQc234Y6F8DRlYa0RJ\n" +
            "5gQEetGoi9UEYWMhYezsXMxGHb8eXz3AU/LEPtQIIc9m0iTPa9Ap4vPsw2AbEvhl1d5GIKhrpQlp\n" +
            "s2AWB4Z5g5BL8EMLHvwSzE7aKdoYwmfzcGMpW4MTk/RWaT5zovnGYHvOkAApC/mC8wJqfj9vpyDi\n" +
            "9jyxelVIDWAvBlGGoBRuOIjSJfHo18Uj1ayAEf/WCBxhnBwAOUjKu6nEBdX31pT1a+60Jbv+mbAw\n" +
            "/4FmH5FvbuKhkWkztL1UR/qae6FVi+2DLmGNHmkO7Na9QZNuHzxp6/Tdeqe8krcmqjKblar9gH4g\n" +
            "0cKwOkYXK4CapS3I8UsyH2C+7xIxoqnzukpcmGDmUftnlvHeUrEZv92z3LhP26wfVfhVL1W/PgB9\n" +
            "qLJOiTP8kZme6EeeWMYbavhyFoqrc1JU0w65QkNO05uYfYR0nx6xqJzaZMakNy42D0uCXJjiZZau\n" +
            "NRVj/1Whjcfja95gnXy+RJb3KCp8JnAHDmH/mD6ZZLTkBFXQGCqcAjRQhSOoceNusaj3gIUGBaWg\n" +
            "maXdbRzYx2r9Zajh8+z9Fd09HeCkC31yZHIJAIsI1wS09LZmOGLRN3V6Utb02AObj2kBuk3KiQV2\n" +
            "RtkQgidKH75+qFzlSrScpxIC4d+pGNLn6yvKy7X6mSm3bdKUWaoSPtsEeNR7iODdupfY4i+34KnK\n" +
            "KbzzOUOXKUXOGoTCbBp6O1hAW18y/tDT1TuS7y+mRYc4zU/yeG3Z5Te6jMMEVP+ejU8BbWlDc78c\n" +
            "3QwtbDYYDvPrmRhliI+K07v4imP1EoSIzKJYsdJKvl6pk/X1avzPSAgAzIuDf74ZkEeEvDv030H7\n" +
            "bvbD7T1P97ZeNloi7zXDwE+FFFDN7OOs5Cw0IxVipo80rU8CebSQ7CELHQ3uxQQJ7Xc/t8GmIJ7c\n" +
            "q+B4ivqrgDsNp4sx1r2c8hxCEL8mnH4RgewWawhedXWGOdxopoUBf/UZeouMokb7c4l9GUvWTeEX\n" +
            "5vOweS8C3R/VBg0dWkwdI9kA4ehl+xB0TqipkrIiPqP4jJTomy5XFdpO5tLGQEeD1aBTywCdn3am\n" +
            "HBrURAcLduv5nEU2xAyfPfsfssnMue+GXfd5elQpSii8i60VfAUpZf5zytM04xWZqQxtnsA6DP1k\n" +
            "+Dfne1OS9/INGsBM05lwCszLlZ1Of8rFfLPqCeBx6ZLn6xSP9uzDWru+/1xYpuUvsw4Or7Y1b925\n" +
            "+1S8oNmuBt1UAVkYHBsuTGmGcfLJOLdWUM0lANpo5oyw7JyQF07PRj1cq7dsjWGxp9h5mN8d/pE6\n" +
            "zQlSp5MblsnDMSmfDX3CGqmOLbSPFqaSvOTWCI2OHswsNk8hxMecyXYEqc6AqRoveoIT4Z5iiWTt\n" +
            "P5IkgFfcoHnJeYBrWrXlS/ymKBJixTo9NuLp7uriJSWkgPAnt0vmEt/7J7xBxElnGkBhoQDrnGva\n" +
            "GCk4S7ZqJCihUngJMSOWU+fpeH3RDY54S44q6xXjX7Y/2E05cBAxvspFuCNFa0MhCHEO1QRgYDr6\n" +
            "MiZaO7MF6Ox0nesGdukeHUef3yPHwtzUgBvRZsCmjchDBN2Ptbx/sSevfTW3dx/npdh0HWDaYeLK\n" +
            "8strK5/y+vq1gC0JDixA64NgDvTAui0GaPr2zg3Oj8nk7Hs+UyLB5Uge5DhoUkHC9ymnAPwjC6bR\n" +
            "ANyhFKr5zglr8NvLBaisoh1q12ZFsHGTm6AWhuWW/Tt810tEcE1DcB5fxJ66N/ZiUiVftL5i8CTA\n" +
            "V2QzcW25LFCSDDNrnY4Z4NVE+1iLnYqoPQPao0jfigHmsrKHkIiavWlSs7aDjBa0duaa+aAoBlTI\n" +
            "v9EQPBVJTEAfzfEXAR8XSgJuTLiBVbZWj9k2sbpiV0e/jywAZhTuyRDTeWT/Ke+DH9Vcom/mygvT\n" +
            "cNQbpY0RPDAO0K/NNAmQuE6v/qrAPR2GfIeTKpl2+m3sJoufAlxllbrapKIPdRtrgnTT4pgANYqa\n" +
            "4RAIBjomdC4o2vpmoZjgfK3DwPdrRc48uOOZ8azYbYaUpzc1q0FP9XFrvqJGLBwAO0sZSLibDpmK\n" +
            "uWmKN4bTCSUKJAf/qEF4mfpfzaoT/T5/DjkdYs9ru8lA7KeF2ZIfM4ZwW1oVRRUsu0Ibe/2cKYLF\n" +
            "+T2bWTEcSlbt7eU2E3JMKu2PkC7vCCVKU8vlaoVCH88M3gebFLe0E9Tk3/ZE3sEMQ63Sc12R23L6\n" +
            "KW1lVIw5wpzB6RvrWQfhuWOQW1M/cno6s2dj0FuOalQrPd9FDOzzZwM30QLIB8pY3AThS+tfKGgR\n" +
            "PfXDj9I29LghxkDn7+n3Woq2LgDR6ilZL80hMBaIOM+0S+rs2nI7JDNd54BxjeMv6U6ML4pvhsqX\n" +
            "XGZlHxt7ons5My8KEip3649/Fga5At/HjkO8jxoYFLvE64Ay8va5xw0x8ASYXIO6Rhyum03UCKBa\n" +
            "0LvJ4SOg5HhZ3iL1bKBcs+8li48E+72D8xP89Z75eAOssceaRN6IJrO/bEcw83DEu5ZR7F2YjjEH\n" +
            "VhMbMvK0SILlA1kLnbtG2qf98YA0Ou9E5dkk63Qqjlxf/M6CcXBcSNgToati24ZthZSfH76yE6/U\n" +
            "8867mvRJxhqz8GfPjfcFxaDVlzoa7xn9U8+YKwXs5NazKaxAtjMcqxP3SVFFB1AgN/GPXy6iaCsd\n" +
            "gTC9E1c2RqIBs0RvQ0JTy4PhKNFw7rjSG8gH87HQUDRRy4aPhSZaSLNmKvQDqxKzYwgwVed0SGiw\n" +
            "j6WPhmdHvA3kTDuJPKcwm7kNTf71oHQLFjEfUFFPmYmaamYfiaY26SL44mEvoq/5OF40X03TU0GB\n" +
            "sNjTkCoqFCME+I9zvh1CxG50J74wQY21CNxWN4TYZsTM9QhPmEr+CnSXDQsYcA4MHCdsNTqGoZ5x\n" +
            "a8+OaxUR9PERjFmpt58lDAOmATjVkJaMp0EMBzPPuYFi/Ndw2cQ/JW5/xCrA/hvdYizJoA7vIo5e\n" +
            "o/yZuBUPsfogADFGjy5XcD1b7AaQJtVziHLkf5CcQYGpqVMBmC6V4kDFfRcOzKvAz2E5sH25RA==\n" +
            "</Data>\n" +
            "   <DeviceInfo dc=\"95d37eb3-13f4-475c-a32a-df9b01d7d164\" dpId=\"MANTRA.MSIPL\" mc=\"MIIEGTCCAwGgAwIBAgIGAXRTCXsdMA0GCSqGSIb3DQEBCwUAMIHpMSowKAYDVQQDEyFEUyBNYW50cmEgU29mdGVjaCBJbmRpYSBQdnQgTHRkIDUxTTBLBgNVBDMTREIgMjAzIFNoYXBhdGggSGV4YSBvcHBvc2l0ZSBHdWphcmF0IEhpZ2ggQ291cnQgUyBHIEhpZ2h3YXkgQWhtZWRhYmFkMRIwEAYDVQQJEwlBaG1lZGFiYWQxEDAOBgNVBAgTB0d1amFyYXQxEjAQBgNVBAsTCVRlY2huaWNhbDElMCMGA1UEChMcTWFudHJhIFNvZnRlY2ggSW5kaWEgUHZ0IEx0ZDELMAkGA1UEBhMCSU4wHhcNMjAwOTAzMDgwMTA2WhcNMjAxMDAzMDgxNTUzWjCBsDEkMCIGCSqGSIb3DQEJARYVc3VwcG9ydEBtYW50cmF0ZWMuY29tMQswCQYDVQQGEwJJTjEQMA4GA1UECBMHR1VKQVJBVDESMBAGA1UEBxMJQUhNRURBQkFEMQ4wDAYDVQQKEwVNU0lQTDEeMBwGA1UECxMVQmlvbWV0cmljIE1hbnVmYWN0dXJlMSUwIwYDVQQDExxNYW50cmEgU29mdGVjaCBJbmRpYSBQdnQgTHRkMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvB2bcX3RgjF52eIdLGs54rHYBoVqij0wgCU247rNeyLxQWdIigma4DjXbwqzJ9waG1PSp2NIoKf+QDKW4ioFAAIjWwwb1AV9XEB0b6WsMA3/+EXsu+oCU//0HwAD3FmjHeoNnPLsncKUOfbxPsYv8dRIKa1qWXw6Wtnew9+TDB6YC+PGIzCp7CSBr3EDmWA/ks3UaDJMkATPqZwWN5BiR6ajLVrhQzRtAQwg2FoNQl4h5XiU8Y+qGZKeRiDz48rahrYKbYgj2zlpIKjWHQfkHvnc4ZOf3hQHesUvqkmO04L5+/QjrcX0Y0WUDshiqEk7WLvXOcnIyk2YZ1PAnN3pawIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQBGl2nGkELFiarPXAmMOuhSuHJFzGW+wVcOlIH1h8qtd6DD03FMEokLj6U+GX2g1w//dM8mJd8Ob5rtsSlTwEFZbD4VoFNojMiNRl7OUkWbIr46xxZC14iOvRhYtjuiMEEs+f/uG4QHuvmJgJ4iCYrILfxlux1LnHR70HCYQaqXnj+QcZoyzci2NwIp+T41MA+dvf7K4iSSQKEbL9eUnlwte0bJLEDYWUu4ij8WqdzxMejciFJ9hPMiiBVMFhom1o7l8BL0EoZkQMpEQX9Kt2BKK4Eb+/JsvbVtlOTpttzzM/UCUblrL0fCVRvY0f27VxzNAS7+08V8/dG/lIi8IWzm\" mi=\"MIS100V2\" rdsId=\"MANTRA.AND.002\" rdsVer=\"1.0.1\">\n" +
            "      <additional_info>\n" +
            "         <Param name=\"srno\" value=\"3345473\"/>\n" +
            "         <Param name=\"sysid\" value=\"862349033669399\"/>\n" +
            "         <Param name=\"ts\" value=\"2020-09-10T15:54:42+05:30\"/>\n" +
            "      </additional_info>\n" +
            "   </DeviceInfo>\n" +
            "   <Hmac>A2VARa5o/+Y1AmLWd6Bc/zg1apPuGNZOcrBOeEm7FpCrUcwInycA/BrGF2fVsEvo\n" +
            "</Hmac>\n" +
            "   <Resp errCode=\"0\" errInfo=\"Capture Success\" fCount=\"0\" fType=\"0\" iCount=\"1\" iType=\"0\" nmPoints=\"0\" pCount=\"0\" pType=\"0\" qScore=\"54\"/>\n" +
            "   <Skey ci=\"20201030\">wxyVDDbYVHC3kwiVwToLbz1baUDhwru5Oj8nmBem29Q7LUJ/HkGgiWeIRtwjjmprRj/wR1MPdbTz\n" +
            "2eerJ++EpNfiwVgQWuotsVRQ4FsFzRDKeePlVvm5pTpXCrHfI+0GtQ5AoOsfD6IJiUH7yjLX6Px3\n" +
            "/OEdurDV3PmI6zFz1TxqPZp0NLMPDQeaL+ujpm53N0sJdcu1wmaE+/72Jbn/OY+c/Pxnbf7B/X9X\n" +
            "lwqjpDOumdv+f3t3I0JFc2tWGN+a4V2e+EE8PyQsAB+N8hzJaRDSrXyTfR6sEBNQGYqK6lT9E1yg\n" +
            "GoaTU35LPmCGYxGlQ9MQerbvwpa7h33tP4G8kg==\n" +
            "</Skey>\n" +
            "</PidData>";

    public static String trim(String input) {
        BufferedReader reader = new BufferedReader(new StringReader(input));
        StringBuffer result = new StringBuffer();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line.trim());
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
