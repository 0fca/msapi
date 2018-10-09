<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>API</title>
        <style>
            * {
    margin: 0px 0px 0px 0px;
    padding: 0px 0px 0px 0px;
}


div.main_page {
    position: relative;
    display: table;
    width: 800px;
    margin-bottom: 3px;
    margin-left: auto;
    margin-right: auto;
    padding: 0px 0px 0px 0px;
    border-width: 2px;
    border-color: #212738;
    border-style: solid;
    background-color: #eee;
    text-align: center;
}

div.page_header {
    height: 99px;
    width: 100%;
    background-color: #095C9E;
}

    div.page_header span {
        width: 100%;
        font-size: 180%;
        font-weight: bold;
    }

    div.page_header img {
        margin: 3px 0px 0px 40px;
    }

div.table_of_contents {
    clear: left;
    min-width: 200px;
    margin: 3px 3px 3px 3px;
    background-color: #FFFFFF;
    text-align: left;
}

div.table_of_contents_item {
    clear: left;
    width: 100%;
    margin: 4px 0px 0px 0px;
    background-color: #FFFFFF;
    color: #000000;
    text-align: left;
}

    div.table_of_contents_item a {
        margin: 6px 0px 0px 6px;
    }

div.content_section {
    margin: 3px 3px 3px 3px;
    background-color: #FFFFFF;
    text-align: left;
}

div.content_section_text {
    padding: 4px 8px 4px 8px;
    color: #000000;
    font-size: 100%;
}

    div.content_section_text pre {
        margin: 8px 0px 8px 0px;
        padding: 8px 8px 8px 8px;
        border-width: 1px;
        border-style: dotted;
        border-color: #000000;
        background-color: #F5F6F7;
        font-style: italic;
    }

    div.content_section_text p {
        margin-bottom: 6px;
    }

    div.content_section_text ul, div.content_section_text li {
        padding: 4px 8px 4px 16px;
    }

div.section_header {
    padding: 3px 6px 3px 6px;
    background-color: #8E9CB2;
    color: #FFFFFF;
    font-weight: bold;
    font-size: 112%;
    text-align: center;
}

div.section_header_red {
    background-color: #095C9E;
}

div.section_header_grey {
    background-color: #9F9386;
}

.floating_element {
    float: left;
    text-align: left;
    margin-bottom: 10px;
}

div.table_of_contents_item a,
div.content_section_text a {
    text-decoration: none;
    font-weight: bold;
}

    div.table_of_contents_item a:link,
    div.table_of_contents_item a:visited,
    div.table_of_contents_item a:active {
        color: #000000;
    }

    div.table_of_contents_item a:hover {
        background-color: #000000;
        color: #FFFFFF;
    }

    div.content_section_text a:link,
    div.content_section_text a:visited,
    div.content_section_text a:active {
        background-color: #DCDFE6;
        color: #000000;
    }

    div.content_section_text a:hover {
        background-color: #000000;
        color: #DCDFE6;
    }
        </style>
    </head>
    <body>
            <div class="container body-content">
              <div class="main_page">
                <div class="page_header floating_element">
                  <span class="floating_element">
                    <img src="/images/logooftheyear2018.png" alt="pika"/>
                  </span>
                  <div style="width: 100%; text-align: center; font-size: 25px;">
                    <strong>API Test Page</strong>
                  </div>
                </div>
                <div class="content_section floating_element">
                  <div class="section_header section_header_red">
                    <div id="about"></div>
                    If you can see this page it means that API is working properly and the server answered with HTTP 200 OK.
                  </div>
                  <div class="content_section_text">
                        This is a test page of Management System API.<br/>
                        From here you should <strong>NOT</strong> try do anything else than just reading this information.<br/>
                        To cooperate with the API use CLI/GUI client.<br/>
                        Download it from <a href="http://me.lukas-bownik.net/msclient.jar">here</a>
                  </div>
                </div>
              </div>
    </body>
</html>