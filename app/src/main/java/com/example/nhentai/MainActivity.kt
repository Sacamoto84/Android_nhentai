package com.example.nhentai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.parser.stringToDynamicHentai
import com.example.nhentai.ui.theme.NhentaiTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NhentaiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}




@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {


    Button(onClick = {


        GlobalScope.launch {

            val a = stringToDynamicHentai(readHtmlFromURL())


        }





    }) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NhentaiTheme {
        Greeting("Android")
    }
}


val html = """
    <!doctype html>
    <html lang="en" class="theme-black">
    <head>
    <meta charset="utf-8" />
    <meta name="theme-color" content="#1f1f1f" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes, viewport-fit=cover" />
    <meta name="description" content="Read and download Uchi no Foreigner ga Toile no Basho mo Mamoranai Warui Neko datta node Butsuriteki Shudan de Shitsukeru Hanashi, a hentai doujinshi by sekai saisoku no panda for free on nhentai.
    " />
    <meta name="csrf-token" content="O2ClJfxTJKLzTzH9X4cNAUBZLOU5DHGmRdJFPzbg">
    <title>(COMIC1☆15) [Nyala Ponga (Sekai Saisoku no Panda)] Uchi no Foreigner ga Toile no Basho mo Mamoranai Warui Neko datta node Butsuriteki Shudan de Shitsukeru Hanashi (Fate/Grand Order) [English] [Kuraudo] » nhentai: hentai doujinshi and manga
    </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans:400,400i,700" />
    <link rel="stylesheet" href="/css/main_style.min.css?1.0.0" />

    <link rel="icon" type="image/png" sizes="32x32" href="/favicon.ico">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" />
    <style>
            .color-icon {
                color: #ed2553;
            }
            nav .menu li.menu-register>a {
                background-color: #ed2553;
            }
            nav .menu li.menu-register>a:hover {
                background-color: #f15478;
            }
            [v-cloak] {
                display: none;
            }
            nav .menu li img {
                vertical-align: middle;
                border-radius: 100%;
            }
        </style>

    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-236315181-1" type="6c39c69e97d6d3bd2ea416a0-text/javascript"></script>
    <script type="6c39c69e97d6d3bd2ea416a0-text/javascript">
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());

      gtag('config', 'UA-236315181-1');
    </script>
    </head>
    <body>
    <nav role="navigation">
    <a class="logo" href="/">
    <img src="/img/logo.650c98bbb08e.svg" alt="logo" width="46" height="30">
    </a>
    <form role="search" action="/search/" class="search">
    <input type="search" name="q" value="" autocapitalize="none" required /><button type="submit" class="btn btn-primary btn-square"><i class="fa fa-search fa-lg"></i></button>
    </form>
    <button type="button" class="btn btn-secondary btn-square" id="hamburger">
    <span class="line"></span>
    <span class="line"></span>
    <span class="line"></span>
    </button>
    <div class="collapse">
    <ul class="menu left">
    <li class="desktop "><a href="/random/">Random</a></li>
    <li class="desktop "><a href="/tags/">Tags</a></li>
    <li class="desktop "><a href="/artists/">Artists</a></li>
    <li class="desktop "><a href="/characters/">Characters</a></li>
    <li class="desktop "><a href="/parodies/">Parodies</a></li>
    <li class="desktop "><a href="/groups/">Groups</a></li>
    <li><a href="https://daftsex.gg/">Daftsex</a></li>
    <li class="dropdown">
    <button class="btn btn-secondary btn-square" type="button" id="dropdown"><i class="fa fa-chevron-down"></i></button>
    <ul class="dropdown-menu">
    <li><a href="/random/">Random</a></li>
    <li><a href="/tags/">Tags</a></li>
    <li><a href="/artists/">Artists</a></li>
    <li><a href="/characters/">Characters</a></li>
    <li><a href="/parodies/">Parodies</a></li>
    <li><a href="/groups/">Groups</a></li>
    </ul>
    </li>
    </ul>
    <ul class="menu right">
     <li class="menu-sign-in">
    <a href="/login/?next=/"><i class="fa fa-sign-in-alt"></i> Sign in</a>
    </li>
    <li class="menu-register">
    <a href="/register/"><i class="fa fa-edit"></i> Register</a>
    </li>
    </ul>
    </div>
    </nav>
    <div id="messages">
    </div>
    <div id="content">
    <iframe width="300" height="250" scrolling="no" frameborder="0" src=https://a.adtng.com/get/10013434?time=1651520347402 allowtransparency="true" marginheight="0" marginwidth="0" name="spot_id_10013434"></iframe>
    </div>
    <div class="container" id="bigcontainer">
    <div id="cover">
    <a href="/g/403147/1/">

    <img width="350" src="https://img.dogehls.xyz/galleries/2220556/cover.png" alt="" />
    <noscript>
                        <img src="https://img.dogehls.xyz/galleries/2220556/cover.png" width="350" height="497" alt="" />
                    </noscript>
    </a>
    </div>
    <div id="info-block">
    <div id="info">
    <h1>(COMIC1☆15) [Nyala Ponga (Sekai Saisoku no Panda)] Uchi no Foreigner ga Toile no Basho mo Mamoranai Warui Neko datta node Butsuriteki Shudan de Shitsukeru Hanashi (Fate/Grand Order) [English] [Kuraudo]</h1>
    <h2>(COMIC1☆15) [ニャリャポンガ (世界最速のパンダ)] ウチのフォーリナーがトイレの場所も守らない悪い猫だったので物理的手段で躾ける話 (Fate/Grand Order) [英訳]</h2>
    <h3 id="gallery_id"><span class="hash">#</span>403147</h3>
    <section id="tags">
    <div class="tag-container field-name ">
    Parodies
    <span class="tags">
    <a href="/parody/fate-grand-order/" class="tag tag-36654 ">
    <span class="name">fate grand order</span>
    <span class="count">11483</span>
    </a>
    </span>
    </div>
    <div class="tag-container field-name ">
    Characters
    <span class="tags">
    <a href="/character/gudao/" class="tag tag-40169 ">
    <span class="name">gudao</span>
    <span class="count">5130</span>
    </a>
    <a href="/character/abigail-williams/" class="tag tag-47208 ">
    <span class="name">abigail williams</span>
    <span class="count">356</span>
    </a>
    </span>
    </div>
    <div class="tag-container field-name ">
    Tags
    <span class="tags">
    <a href="/tag/chastity-belt/" class="tag tag-190 ">
    <span class="name">chastity belt</span> 
    <span class="count">1483</span>
    </a>
    <a href="/tag/urination/" class="tag tag-196 ">
    <span class="name">urination</span>
    <span class="count">8784</span>
    </a>
    <a href="/tag/lolicon/" class="tag tag-4 ">
    <span class="name">lolicon</span>
    <span class="count">81366</span>
    </a>
    <a href="/tag/stockings/" class="tag tag-129 ">
    <span class="name">stockings</span>
    <span class="count">79889</span>
    </a>
    <a href="/tag/catgirl/" class="tag tag-8 ">
    <span class="name">catgirl</span>
    <span class="count">9644</span>
    </a>
    <a href="/tag/sole-female/" class="tag tag-230 ">
    <span class="name">sole female</span>
    <span class="count">100046</span>
    </a>
    <a href="/tag/sole-male/" class="tag tag-236 ">
    <span class="name">sole male</span>
    <span class="count">89381</span>
    </a>
    <a href="/tag/kemonomimi/" class="tag tag-46533 ">
    <span class="name">kemonomimi</span>
    <span class="count">11376</span>
    </a>
    </span>
    </div>
    <div class="tag-container field-name ">
    Artists
    <span class="tags">
    <a href="/artist/sekai-saisoku-no-panda/" class="tag tag-48602 ">
    <span class="name">sekai saisoku no panda</span>
    <span class="count">31</span>
    </a>
    </span>
    </div>
    <div class="tag-container field-name ">
    Groups
    <span class="tags">
    <a href="/group/nyala-ponga/" class="tag tag-48603 ">
    <span class="name">nyala ponga</span>
    <span class="count">30</span>
    </a>
    </span>
    </div>
    <div class="tag-container field-name ">
    Languages
    <span class="tags">
    <a href="/language/english/" class="tag tag-19 ">
    <span class="name">english</span>
    <span class="count">92002</span>
     </a>
    <a href="/language/translated/" class="tag tag-17 ">
    <span class="name">translated</span>
    <span class="count">157067</span>
    </a>
    </span>
    </div>
    <div class="tag-container field-name ">
    Categories
    <span class="tags">
    <a href="/category/doujinshi/" class="tag tag-9 ">
    <span class="name">doujinshi</span>
    <span class="count">291950</span>
    </a>
    </span>
    </div>
    <div class="tag-container field-name">
    Pages:
    <span class="tags">
    <a href="#" class="tag">
    <span class="name">29</span>
    </a>
    </span>
    </div>
    <div class="tag-container field-name">
    Uploaded:
    <span class="tags"><time datetime="2023-02-02T16:18:17+00:00">02/02/2023 04:18 PM</time></span>
    </div>
    </section>
    <div class="buttons">
    <a class="btn btn-primary btn-disabled tooltip"><i class="fa fa-heart"></i><span>Favorite <div class="top">You need to log in to add favorites<i></i></div></span></a>
    <a id="download" class="btn btn-secondary btn-disabled tooltip"> <i class="fa fa-download"></i> Download<div class="top">You need to log in to download<i></i></div></a>
    </div>
    </div>
    </div>
    </div>
    <center>
    <iframe width="300" height="250" scrolling="no" frameborder="0" src=https://a.adtng.com/get/10013434?time=1651520347402 allowtransparency="true" marginheight="0" marginwidth="0" name="spot_id_10013434"></iframe>
    &nbsp;&nbsp;
    &nbsp;&nbsp;
    &nbsp;&nbsp;
    <iframe width="300" height="250" scrolling="no" frameborder="0" src=https://a.adtng.com/get/10013434?time=1651520347402 allowtransparency="true" marginheight="0" marginwidth="0" name="spot_id_10013434"></iframe>
    </center>
    <div class="container" id="thumbnail-container">
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/1/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1817" data-src="https://img.dogehls.xyz/galleries/2220556/1t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/1t.png" width="1280" height="1817" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/2/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1853" data-src="https://img.dogehls.xyz/galleries/2220556/2t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/2t.png" width="1280" height="1853" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/3/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1845" data-src="https://img.dogehls.xyz/galleries/2220556/3t.png" alt="" />
     <noscript><img src="https://img.dogehls.xyz/galleries/2220556/3t.png" width="1280" height="1845" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/4/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1828" data-src="https://img.dogehls.xyz/galleries/2220556/4t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/4t.png" width="1280" height="1828" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/5/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1825" data-src="https://img.dogehls.xyz/galleries/2220556/5t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/5t.png" width="1280" height="1825" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/6/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1830" data-src="https://img.dogehls.xyz/galleries/2220556/6t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/6t.png" width="1280" height="1830" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/7/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1829" data-src="https://img.dogehls.xyz/galleries/2220556/7t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/7t.png" width="1280" height="1829" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/8/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1830" data-src="https://img.dogehls.xyz/galleries/2220556/8t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/8t.png" width="1280" height="1830" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/9/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1828" data-src="https://img.dogehls.xyz/galleries/2220556/9t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/9t.png" width="1280" height="1828" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/10/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1830" data-src="https://img.dogehls.xyz/galleries/2220556/10t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/10t.png" width="1280" height="1830" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/11/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1832" data-src="https://img.dogehls.xyz/galleries/2220556/11t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/11t.png" width="1280" height="1832" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/12/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1829" data-src="https://img.dogehls.xyz/galleries/2220556/12t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/12t.png" width="1280" height="1829" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/13/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1825" data-src="https://img.dogehls.xyz/galleries/2220556/13t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/13t.png" width="1280" height="1825" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/14/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1824" data-src="https://img.dogehls.xyz/galleries/2220556/14t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/14t.png" width="1280" height="1824" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/15/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1826" data-src="https://img.dogehls.xyz/galleries/2220556/15t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/15t.png" width="1280" height="1826" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/16/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1834" data-src="https://img.dogehls.xyz/galleries/2220556/16t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/16t.png" width="1280" height="1834" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/17/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1835" data-src="https://img.dogehls.xyz/galleries/2220556/17t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/17t.png" width="1280" height="1835" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/18/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1824" data-src="https://img.dogehls.xyz/galleries/2220556/18t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/18t.png" width="1280" height="1824" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/19/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1821" data-src="https://img.dogehls.xyz/galleries/2220556/19t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/19t.png" width="1280" height="1821" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/20/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1829" data-src="https://img.dogehls.xyz/galleries/2220556/20t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/20t.png" width="1280" height="1829" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/21/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1829" data-src="https://img.dogehls.xyz/galleries/2220556/21t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/21t.png" width="1280" height="1829" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/22/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1827" data-src="https://img.dogehls.xyz/galleries/2220556/22t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/22t.png" width="1280" height="1827" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/23/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1829" data-src="https://img.dogehls.xyz/galleries/2220556/23t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/23t.png" width="1280" height="1829" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/24/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1827" data-src="https://img.dogehls.xyz/galleries/2220556/24t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/24t.png" width="1280" height="1827" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/25/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1833" data-src="https://img.dogehls.xyz/galleries/2220556/25t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/25t.png" width="1280" height="1833" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/26/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1856" data-src="https://img.dogehls.xyz/galleries/2220556/26t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/26t.png" width="1280" height="1856" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/27/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1853" data-src="https://img.dogehls.xyz/galleries/2220556/27t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/27t.png" width="1280" height="1853" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/28/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1853" data-src="https://img.dogehls.xyz/galleries/2220556/28t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/28t.png" width="1280" height="1853" alt="" /></noscript>
    </a>
    </div>
    <div class="thumb-container">
    <a class="gallerythumb" href="/g/403147/29/" rel="nofollow">
    <img is="lazyload-image" class="lazyload" width="1280" height="1817" data-src="https://img.dogehls.xyz/galleries/2220556/29t.png" alt="" />
    <noscript><img src="https://img.dogehls.xyz/galleries/2220556/29t.png" width="1280" height="1817" alt="" /></noscript>
    </a>
    </div>
    </div>
    <div class="container index-container">
    <h2>More Like This</h2>
    <div class="gallery" data-tags="19 17 36654 36769 48603 48602 73 181 1762 235 11 169 681 9 1545 29 30 556 129 40169">
    <a href="/g/253680/" class="cover" style="padding:0 0 142.79999999999998% 0">

    <img is="lazyload-image" class="lazyload " width="250" height="357" data-src="https://img.dogehls.xyz/galleries/1317614/thumb.jpg" alt="" />
    <noscript>
                        <img src="https://img.dogehls.xyz/galleries/1317614/thumb.jpg" width="250" height="357" alt="" />
                    </noscript>
    <div class="caption">(C93) [Nyala Ponga (Sekai Saisoku no Panda)] Astolfo-kun to Ryoushi Koukan Shiau Hon (Fate Grand Order) [English] [mysterymeat3]</div>
    </a>
    </div>
    <div class="gallery" data-tags="36654 1096 30160 48603 48602 76 174 4 11 9 2 106 236 40169">
    <a href="/g/250495/" class="cover" style="padding:0 0 142.79999999999998% 0">

    <img is="lazyload-image" class="lazyload " width="250" height="357" data-src="https://img.dogehls.xyz/galleries/1303544/thumb.jpg" alt="" />
    <noscript>
                        <img src="https://img.dogehls.xyz/galleries/1303544/thumb.jpg" width="250" height="357" alt="" />
                    </noscript>
    <div class="caption">(COMIC1☆14) [Nyala Ponga (Sekai Saisoku no Panda)] Koakuma Codes (Fate/Grand Order)</div>
    </a>
    </div>
    <div class="gallery" data-tags="36654 36769 48603 48602 73 29 181 1762 11 556 30 129 169 681 9 2 40169 173">
    <a href="/g/250345/" class="cover" style="padding:0 0 142.79999999999998% 0">

    <img is="lazyload-image" class="lazyload " width="250" height="357" data-src="https://img.dogehls.xyz/galleries/1302825/thumb.jpg" alt="" />
    <noscript>
                        <img src="https://img.dogehls.xyz/galleries/1302825/thumb.jpg" width="250" height="357" alt="" />
                    </noscript>
    <div class="caption">(C93) [Nyala Ponga (Sekai Saisoku no Panda)] Astolfo-kun to Ryoushi Koukan Shiau Hon (Fate/Grand Order)</div>
    </a>
    </div>
    <div class="gallery" data-tags="27069 37969 48603 48602 160 454 4 230 129 58 173 9 2">
    <a href="/g/249682/" class="cover" style="padding:0 0 142.79999999999998% 0">

    <img is="lazyload-image" class="lazyload " width="250" height="357" data-src="https://img.dogehls.xyz/galleries/1299896/thumb.jpg" alt="" />

    <noscript>
                        <img src="https://img.dogehls.xyz/galleries/1299896/thumb.jpg" width="250" height="357" alt="" />
                    </noscript>
    <div class="caption">(Panzer Vor! 18) [Nyala Ponga (Sekai Saisoku no Panda)] Shimada Arisu-chan de Manabu Onnanoko no Jintai &lt;Kotsuban Hen&gt; Jou (Girls und Panzer)</div>
    </a>
    </div>
    <div class="gallery" data-tags="10197 17 36654 36769 73 181 1762 11 30 129 169 681 9 40169 48602 48603 9132">
    <a href="/g/231892/" class="cover" style="padding:0 0 142.79999999999998% 0">

    <img is="lazyload-image" class="lazyload " width="250" height="357" data-src="https://img.dogehls.xyz/galleries/1219189/thumb.jpg" alt="" />
    <noscript>
                        <img src="https://img.dogehls.xyz/galleries/1219189/thumb.jpg" width="250" height="357" alt="" />
                    </noscript>
    <div class="caption">(C93) [Nyala Ponga (Sekai Saisoku no Panda)] Astolfo-kun to Ryoushi Koukan Shiau Hon (Fate/Grand Order) [Chinese] [瑞树汉化组]</div>
    </a>
    </div>
    </div>
    <div class="container index-container">
    <div id="disqus_thread"></div>
    <script type="6c39c69e97d6d3bd2ea416a0-text/javascript">
                /**
                *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
                *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables    */
                
                var disqus_config = function () {
                    this.page.url = 'https://nhentai.to/g/403147';  
                    this.page.identifier = 'g/403147';
                };
                
                (function() { // DON'T EDIT BELOW THIS LINE
                var d = document, s = d.createElement('script');
                s.src = 'https://nhentai-to-1.disqus.com/embed.js';
                s.setAttribute('data-timestamp', +new Date());
                (d.head || d.body).appendChild(s);
                })();
            </script>
    </div>
    </div>
    <script src="/js/combined.1d8cbb9eacc8.js" type="6c39c69e97d6d3bd2ea416a0-text/javascript"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5/dist/vue.js" type="6c39c69e97d6d3bd2ea416a0-text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.0/axios.min.js" type="6c39c69e97d6d3bd2ea416a0-text/javascript"></script>
    <script type="6c39c69e97d6d3bd2ea416a0-text/javascript">
            N.init({
                "csrf_token": "O2ClJfxTJKLzTzH9X4cNAUBZLOU5DHGmRdJFPzbg"
            })
            var gallery = new N.gallery({
                "id": 392735,
                "media_id": "2220556",
                "title": {
                    "english": "(COMIC1☆15) [Nyala Ponga (Sekai Saisoku no Panda)] Uchi no Foreigner ga Toile no Basho mo Mamoranai Warui Neko datta node Butsuriteki Shudan de Shitsukeru Hanashi (Fate/Grand Order) [English] [Kuraudo]",
                    "japanese": "(COMIC1☆15) [ニャリャポンガ (世界最速のパンダ)] ウチのフォーリナーがトイレの場所も守らない悪い猫だったので物理的手段で躾ける話 (Fate/Grand Order) [英訳]",
                    "pretty": "Uchi no Foreigner ga Toile no Basho mo Mamoranai Warui Neko datta node Butsuriteki Shudan de Shitsukeru Hanashi"
                },
                "images": {
                    "pages": {"2":{"t":"p","w":1280,"h":1817},"3":{"t":"p","w":1280,"h":1853},"4":{"t":"p","w":1280,"h":1845},"5":{"t":"p","w":1280,"h":1828},"6":{"t":"p","w":1280,"h":1825},"7":{"t":"p","w":1280,"h":1830},"8":{"t":"p","w":1280,"h":1829},"9":{"t":"p","w":1280,"h":1830},"10":{"t":"p","w":1280,"h":1828},"11":{"t":"p","w":1280,"h":1830},"12":{"t":"p","w":1280,"h":1832},"13":{"t":"p","w":1280,"h":1829},"14":{"t":"p","w":1280,"h":1825},"15":{"t":"p","w":1280,"h":1824},"16":{"t":"p","w":1280,"h":1826},"17":{"t":"p","w":1280,"h":1834},"18":{"t":"p","w":1280,"h":1835},"19":{"t":"p","w":1280,"h":1824},"20":{"t":"p","w":1280,"h":1821},"21":{"t":"p","w":1280,"h":1829},"22":{"t":"p","w":1280,"h":1829},"23":{"t":"p","w":1280,"h":1827},"24":{"t":"p","w":1280,"h":1829},"25":{"t":"p","w":1280,"h":1827},"26":{"t":"p","w":1280,"h":1833},"27":{"t":"p","w":1280,"h":1856},"28":{"t":"p","w":1280,"h":1853},"29":{"t":"p","w":1280,"h":1853},"30":{"t":"p","w":1280,"h":1817}},
                    "cover": {"t":"p","w":350,"h":497},
                    "thumbnail": {"t":"p","w":250,"h":355}            },
                "scanlator": "",
                "upload_date": 1675354697,
                "tags": [{"id":190,"nh_id":"3455","type":"tag","name":"chastity belt","url":"\/tag\/chastity-belt\/","created_at":"2020-05-13T02:35:15.000000Z","updated_at":"2020-07-27T02:32:03.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":190}},{"id":196,"nh_id":"10476","type":"tag","name":"urination","url":"\/tag\/urination\/","created_at":"2020-05-13T02:35:49.000000Z","updated_at":"2020-07-29T18:05:44.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":196}},{"id":19,"nh_id":"12227","type":"language","name":"english","url":"\/language\/english\/","created_at":"2020-05-13T01:24:35.000000Z","updated_at":"2020-07-29T18:09:40.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":19}},{"id":17,"nh_id":"17249","type":"language","name":"translated","url":"\/language\/translated\/","created_at":"2020-05-13T01:24:35.000000Z","updated_at":"2020-07-29T18:28:56.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":17}},{"id":4,"nh_id":"19440","type":"tag","name":"lolicon","url":"\/tag\/lolicon\/","created_at":"2020-05-13T01:24:15.000000Z","updated_at":"2020-07-29T23:08:20.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":4}},{"id":129,"nh_id":"24201","type":"tag","name":"stockings","url":"\/tag\/stockings\/","created_at":"2020-05-13T01:34:18.000000Z","updated_at":"2020-07-29T23:08:20.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":129}},{"id":8,"nh_id":"31386","type":"tag","name":"catgirl","url":"\/tag\/catgirl\/","created_at":"2020-05-13T01:24:15.000000Z","updated_at":"2020-07-27T21:46:52.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":8}},{"id":9,"nh_id":"33172","type":"category","name":"doujinshi","url":"\/category\/doujinshi\/","created_at":"2020-05-13T01:24:15.000000Z","updated_at":"2020-07-29T23:08:20.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":9}},{"id":36654,"nh_id":"35605","type":"parody","name":"fate grand order","url":"\/parody\/fate-grand-order\/","created_at":"2020-07-30T06:11:15.000000Z","updated_at":"2020-07-30T06:11:15.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":36654}},{"id":230,"nh_id":"35762","type":"tag","name":"sole female","url":"\/tag\/sole-female\/","created_at":"2020-05-13T02:44:49.000000Z","updated_at":"2020-07-29T18:09:40.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":230}},{"id":236,"nh_id":"35763","type":"tag","name":"sole male","url":"\/tag\/sole-male\/","created_at":"2020-05-13T02:45:16.000000Z","updated_at":"2020-07-29T18:05:13.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":236}},{"id":40169,"nh_id":"51810","type":"character","name":"gudao","url":"\/character\/gudao\/","created_at":"2020-07-31T03:20:42.000000Z","updated_at":"2020-07-31T03:20:42.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":40169}},{"id":47208,"nh_id":"80930","type":"character","name":"abigail williams","url":"\/character\/abigail-williams\/","created_at":"2020-08-01T12:39:13.000000Z","updated_at":"2020-08-01T12:39:13.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":47208}},{"id":46533,"nh_id":"81774","type":"tag","name":"kemonomimi","url":"\/tag\/kemonomimi\/","created_at":"2020-08-01T08:37:03.000000Z","updated_at":"2020-08-01T08:37:03.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":46533}},{"id":48602,"nh_id":"86534","type":"artist","name":"sekai saisoku no panda","url":"\/artist\/sekai-saisoku-no-panda\/","created_at":"2020-08-01T21:28:09.000000Z","updated_at":"2020-08-01T21:28:09.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":48602}},{"id":48603,"nh_id":"86535","type":"group","name":"nyala ponga","url":"\/group\/nyala-ponga\/","created_at":"2020-08-01T21:28:09.000000Z","updated_at":"2020-08-01T21:28:09.000000Z","deleted_at":null,"pivot":{"book_id":392735,"tag_id":48603}}],
                "num_pages": 29,
            });
            gallery.init();

                </script>
    <div><script data-cfasync="false" async type="text/javascript" src="//qt.petwoodfustet.com/t0Bm04roC9K/53202"></script></div>

    <a href="/home/" alt="free page hit counter" target="_blank"></a>
    <img src="//sstatic1.histats.com/0.gif?4392184" alt="free page hit counter" border="0">


    <div style="display:none;">
    <script id="_waup67" type="6c39c69e97d6d3bd2ea416a0-text/javascript">var _wau = _wau || []; _wau.push(["small", "00srr2eyjz", "p67"]);</script><script async src="//waust.at/s.js" type="6c39c69e97d6d3bd2ea416a0-text/javascript"></script>
    </div>

    <ul class="footer-menu">
    <li><a href="/cdn-cgi/l/email-protection#b6d7d2c3dac2ced8d3c2c1d9c4ddddf6d1dbd7dfda98d5d9db">Contact US</a></li>
    <li><a href="https://rehentai.com/">Hentai Manga</a></li>
    <li><a href="https://hentaihaven.xxx/">hentai</a></li>
    <li><a href="https://ehentai.to/">ehentai</a></li>
    <li><a href="https://hentai2read.to/">hentai2read</a></li>
    <li><a href="https://hitomila.to/">hitomila</a></li>
    <li><a href="https://hentaifox.cc/">hentaifox</a></li>
    <li><a href="https://myreadingmanga.to/">Myreadingmanga</a></li>
    <li><a href="https://animixplay.red/">animixplay</a></li>
    <li><a href="https://spankbang.gg/">spankbang</a></li>
    <li><a href="https://hentaizilla.com/hentai-comics/">HentaiZilla</a></li>
    </ul>
    <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script data-cfasync="false">
        var __aaZoneid = 1941825;
        var __aaType = 2;

    (function(_0x4511ca,_0x18c497){var _0x28833e=_0x2dca,_0x38f6c=_0x4511ca();while(!![]){try{var _0x5be587=parseInt(_0x28833e(0x1e6))/0x1*(parseInt(_0x28833e(0x1fa))/0x2)+-parseInt(_0x28833e(0x1ff))/0x3*(parseInt(_0x28833e(0x1fd))/0x4)+-parseInt(_0x28833e(0x1e8))/0x5*(-parseInt(_0x28833e(0x1da))/0x6)+-parseInt(_0x28833e(0x1f5))/0x7*(-parseInt(_0x28833e(0x1f1))/0x8)+-parseInt(_0x28833e(0x1eb))/0x9*(-parseInt(_0x28833e(0x1d1))/0xa)+-parseInt(_0x28833e(0x1ed))/0xb*(-parseInt(_0x28833e(0x1d9))/0xc)+-parseInt(_0x28833e(0x1fb))/0xd*(-parseInt(_0x28833e(0x1f4))/0xe);if(_0x5be587===_0x18c497)break;else _0x38f6c['push'](_0x38f6c['shift']());}catch(_0x19a2c9){_0x38f6c['push'](_0x38f6c['shift']());}}}(_0x8c59,0x1a828),function(_0xd8b97a,_0x448cf4,_0x4c7b9a){var _0x4140e8=_0x2dca,_0x273abc=(function(){'use strict';var _0x54a62d;(function(_0x318e45){var _0x114205=_0x2dca;_0x318e45[_0x318e45[_0x114205(0x1f9)]=0x1]=_0x114205(0x1f9),_0x318e45[_0x318e45['oSu']=0x2]=_0x114205(0x205),_0x318e45[_0x318e45[_0x114205(0x1cf)]=0x3]=_0x114205(0x1cf),_0x318e45[_0x318e45['oDlu']=0x5]=_0x114205(0x1dd),_0x318e45[_0x318e45[_0x114205(0x1f8)]=0x6]=_0x114205(0x1f8);}(_0x54a62d||(_0x54a62d={})));var _0x3549a0=(function(){var _0x4aa1f9=_0x2dca;function _0x45a4a0(_0x25b738,_0x4c39ee,_0x345066){var _0x4f8d46=_0x2dca;this[_0x4f8d46(0x201)]=_0x25b738,this[_0x4f8d46(0x200)]=_0x4c39ee,this[_0x4f8d46(0x20a)]=_0x345066,this['b64d']=_0x4f8d46(0x1f7);}return _0x45a4a0[_0x4aa1f9(0x1f2)]['in']=function(){this['ast']();},_0x45a4a0[_0x4aa1f9(0x1f2)][_0x4aa1f9(0x20c)]=function(){var _0x49b361=_0x4aa1f9,_0x2b6f66=this;Promise[_0x49b361(0x20b)]([this['gcu'](),this[_0x49b361(0x1cd)](),this['gau'](),this[_0x49b361(0x1dc)]()])[_0x49b361(0x1e5)](function(_0x576eb3){var _0x5df4bf=_0x49b361;_0x2b6f66[_0x5df4bf(0x201)][_0x2b6f66[_0x5df4bf(0x1d3)]()]=_0x576eb3;});},_0x45a4a0[_0x4aa1f9(0x1f2)]['gd']=function(_0x3697bd){var _0x337102=_0x4aa1f9,_0x4ff72c=this;_0x3697bd===void 0x0&&(_0x3697bd=this['type']);if(!WebAssembly||!WebAssembly['instantiate'])return Promise[_0x337102(0x1e9)](undefined);var _0x3f96c5=this['b64ab'](this[_0x337102(0x1d2)]);return this['isy'](_0x3f96c5)[_0x337102(0x1e5)](function(_0x96e393){var _0x448487=_0x337102,_0x1c65f0=_0x4ff72c[_0x448487(0x1f0)](_0x3697bd);return _0x96e393[_0x448487(0x1ce)](_0x1c65f0);});},_0x45a4a0[_0x4aa1f9(0x1f2)][_0x4aa1f9(0x1ea)]=function(_0x45d037){return Uint8Array['from'](atob(_0x45d037),function(_0x4c2e24){return _0x4c2e24['charCodeAt'](0x0);});},_0x45a4a0[_0x4aa1f9(0x1f2)][_0x4aa1f9(0x1f0)]=function(_0xe829fc){var _0x318f01=_0x4aa1f9,_0x56c8b6,_0x1f8fd6=((_0x56c8b6=window[_0x318f01(0x1e7)])===null||_0x56c8b6===void 0x0?void 0x0:_0x56c8b6['userAgent'])||'',_0x47cea4=window['location'][_0x318f01(0x204)]||'',_0xdbe474=window[_0x318f01(0x206)],_0x34bf07=window[_0x318f01(0x1ef)],_0x4efa13=window[_0x318f01(0x1e1)]?0x1:0x0;return[_0xdbe474,_0x34bf07,_0x4efa13,Date[_0x318f01(0x1d8)](),0x0,_0xe829fc,_0x47cea4[_0x318f01(0x1d5)](0x0,0x64),_0x1f8fd6[_0x318f01(0x1d5)](0x0,0xf)]['join'](',');},_0x45a4a0['prototype']['ast']=function(){var _0x57b6d9=_0x4aa1f9,_0x3f1710=this;this['gd']()[_0x57b6d9(0x1e5)](function(_0x3a0f2e){var _0x1a5c8e=_0x57b6d9;window[_0x3f1710[_0x1a5c8e(0x1ec)]()]=0x1;var _0x5adcdd=_0x3f1710[_0x1a5c8e(0x201)][_0x1a5c8e(0x1e2)][_0x1a5c8e(0x1de)](_0x1a5c8e(0x208));_0x5adcdd[_0x1a5c8e(0x209)]=_0x3f1710['gfu'](_0x3a0f2e)+_0x1a5c8e(0x1df),_0x3f1710['win'][_0x1a5c8e(0x1e2)][_0x1a5c8e(0x1fc)]['appendChild'](_0x5adcdd);});},_0x45a4a0['prototype']['isy']=function(_0x58b279,_0x28d8cf){var _0x46a3d6=_0x4aa1f9;return _0x28d8cf===void 0x0&&(_0x28d8cf={}),WebAssembly[_0x46a3d6(0x1e0)](_0x58b279,_0x28d8cf)[_0x46a3d6(0x1e5)](function(_0x5cbe95){var _0x55f076=_0x46a3d6,_0x4f6410=_0x5cbe95[_0x55f076(0x1d6)],_0x1e5082=_0x4f6410[_0x55f076(0x202)],_0x1eb1cb=_0x1e5082[_0x55f076(0x1ee)],_0x52b0fd=new TextEncoder(),_0x12cabe=new TextDecoder(_0x55f076(0x1d4));return{'url':function(_0x8a9a43){var _0x41ab45=_0x55f076,_0x4e210f=_0x52b0fd['encode'](_0x8a9a43),_0x2cd182=new Uint8Array(_0x1eb1cb['buffer'],0x0,_0x4e210f[_0x41ab45(0x1e3)]);_0x2cd182['set'](_0x4e210f);var _0x4fc7e6=_0x2cd182[_0x41ab45(0x1f6)]+_0x4e210f[_0x41ab45(0x1e3)],_0x5ebb5e=_0x1e5082[_0x41ab45(0x1ce)](_0x2cd182,_0x4e210f[_0x41ab45(0x1e3)],_0x4fc7e6),_0x3c0720=new Uint8Array(_0x1eb1cb[_0x41ab45(0x1d7)],_0x4fc7e6,_0x5ebb5e);return _0x12cabe[_0x41ab45(0x1fe)](_0x3c0720);}};});},_0x45a4a0['prototype'][_0x4aa1f9(0x1d3)]=function(){var _0x34d29b=_0x4aa1f9;return this['zoneid']+_0x34d29b(0x1f3);},_0x45a4a0[_0x4aa1f9(0x1f2)][_0x4aa1f9(0x1ec)]=function(){var _0x4f8183=_0x4aa1f9;return this[_0x4f8183(0x1d3)]()+_0x4f8183(0x1d0);},_0x45a4a0['prototype'][_0x4aa1f9(0x1e4)]=function(){var _0x451d65=_0x4aa1f9;return this['gd'](_0x54a62d['oCu'])[_0x451d65(0x1e5)](function(_0x3ad1e9){return _0x3ad1e9;});},_0x45a4a0[_0x4aa1f9(0x1f2)][_0x4aa1f9(0x1cd)]=function(){var _0x3dc071=_0x4aa1f9;return this['gd'](_0x54a62d[_0x3dc071(0x1cf)])['then'](function(_0x3fe21b){return _0x3fe21b;});},_0x45a4a0['prototype'][_0x4aa1f9(0x207)]=function(){var _0x54693c=_0x4aa1f9;return this['gd'](_0x54a62d['oAu'])[_0x54693c(0x1e5)](function(_0x150d1b){return _0x150d1b;});},_0x45a4a0[_0x4aa1f9(0x1f2)][_0x4aa1f9(0x1dc)]=function(){var _0x3b488a=_0x4aa1f9;return this['gd'](_0x54a62d['oDlu'])[_0x3b488a(0x1e5)](function(_0x3c2ac4){return _0x3c2ac4;});},_0x45a4a0[_0x4aa1f9(0x1f2)][_0x4aa1f9(0x203)]=function(_0x558c1b){var _0x28f74f=_0x4aa1f9;return _0x558c1b+_0x28f74f(0x1db)+this[_0x28f74f(0x200)];},_0x45a4a0;}()),_0x54e305=_0x3549a0;return _0x54e305;}()),_0x511ecc=new _0x273abc(window,_0xd8b97a,_0x4c7b9a);_0x511ecc[_0x4140e8(0x20c)](),window[_0x448cf4]=function(){_0x511ecc['in']();};}(__aaZoneid,"qweqweew",__aaType));function _0x2dca(_0x412ef9,_0x28d114){var _0x8c593d=_0x8c59();return _0x2dca=function(_0x2dca7a,_0x5c60e7){_0x2dca7a=_0x2dca7a-0x1cd;var _0x268b0f=_0x8c593d[_0x2dca7a];return _0x268b0f;},_0x2dca(_0x412ef9,_0x28d114);}function _0x8c59(){var _0x3157c3=['buffer','now','24MaaMlt','942GKOEsj','?zoneid=','gdlu','oDlu','createElement','&ab=1','instantiate','sessionStorage','document','length','gcu','then','323zvRKeR','navigator','1490cdYWHk','resolve','b64ab','234ofWkGV','giabk','346247ewIFsQ','memory','innerWidth','gfco','1488kPMRqd','prototype','__cngfg','28ydAWKf','1799Vgqbtr','byteOffset','AGFzbQEAAAABHAVgAAF/YAN/f38Bf2ADf39/AX5gAX8AYAF/AX8DCQgAAQIBAAMEAAQFAXABAQEFBgEBgAKAAgYJAX8BQcCIwAILB2cHBm1lbW9yeQIAA3VybAADGV9faW5kaXJlY3RfZnVuY3Rpb25fdGFibGUBABBfX2Vycm5vX2xvY2F0aW9uAAcJc3RhY2tTYXZlAAQMc3RhY2tSZXN0b3JlAAUKc3RhY2tBbGxvYwAGCpAFCCEBAX9BuAhBuAgoAgBBE2xBoRxqQYfC1y9wIgA2AgAgAAuTAQEFfxAAIAEgAGtBAWpwIABqIgQEQEEAIQBBAyEBA0AgAUEDIABBA3AiBxshARAAIgZBFHBBkAhqLQAAIQMCfyAFQQAgBxtFBEBBACAGIAFwDQEaIAZBBnBBgAhqLQAAIQMLQQELIQUgACACaiADQawILQAAazoAACABQQFrIQEgAEEBaiIAIARJDQALCyACIARqC3ECA38CfgJAIAFBAEwNAANAIARBAWohAyACIAUgACAEai0AAEEsRmoiBUYEQCABIANMDQIDQCAAIANqMAAAIgdCLFENAyAGQgp+IAd8QjB9IQYgA0EBaiIDIAFHDQALDAILIAMhBCABIANKDQALCyAGC8ECAgd/An5BuAggACABQQMQAiIKQbAIKQMAIgsgCiALVBtBqAgoAgAiA0EyaiIEIARsQegHbK2AIgsgA0EOaiIIIANBBGsgCkKAgPHtxzBUG62APgIAEAAaEAAaIAJC6OjRg7fOzpcvNwAAQQdBCyACQQhqEAEhAxAAGiMAQRBrIgQkACADQS46AAAgBEHj3rUDNgIMIANBAWohBUEAIQMgBEEMaiIJLQAAIgYEQANAIAMgBWogBjoAACAJIANBAWoiA2otAAAiBg0ACwsgBEEQaiQAIAMgBWohA0G4CCAKQv//8O3HMFZBGnStIAsgCK2AhCAAIAFBBRACQhuGhD4CABAAGkECQQQQAEEDcCIAGyEBA0AgA0EvOgAAIAAgB0YhBCABQQUgA0EBahABIQMgB0EBaiEHIARFDQALIAMgAmsLBAAjAAsGACAAJAALEAAjACAAa0FwcSIAJAAgAAsFAEG8CAsLOwMAQYAICwaeoqassrYAQZAICxSfoKGjpKWnqKmqq62ur7Cxs7S1twBBqAgLDgoAAAA9AAAAAPReUoYB','oAu','oCu','634oFteOa','68341ApEukw','head','52LsCoqm','decode','41277CEsSBc','zoneid','win','exports','gfu','hostname','oSu','innerHeight','gau','script','src','type','all','ins','gru','url','oRu','__ab','6540TaOTlO','b64d','gcuk','utf-8','slice','instance'];_0x8c59=function(){return _0x3157c3;};return _0x8c59();}
    </script>
    <script data-cfasync="false" type="text/javascript" src="//zsxrhkrfwwv.com/aas/r45d/vki/1891388/bd9c5fb9.js" onerror="qweqweew()"></script>
    <script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="6c39c69e97d6d3bd2ea416a0-|49" defer=""></script></body>
    </html>

    
    
""".trimIndent()
