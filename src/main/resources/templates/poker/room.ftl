<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre.min.css" rel="stylesheet">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre-icons.min.css" rel="stylesheet">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre-exp.min.css" rel="stylesheet">
    <link rel='stylesheet' href='https://unpkg.com/emoji.css/dist/emoji.min.css'>
    <link rel='stylesheet' href='/poker/poker.css'>
    <script src='http://libs.baidu.com/jquery/2.1.1/jquery.min.js'></script>
    <script type="text/javascript" src="/poker/poker.js"></script>
    <script type="text/javascript">
        $(function () {
            const url = "/demo/broadcast/subscribe/${roomInfo.oneself.id}/${roomInfo.room.roomNo}"
            eventSource(url);
        })
    </script>
    <title>${roomInfo.room.name?capitalize}'s Room</title>
</head>
<body>
<div class="container">
    <#include "./header.ftl">
    <#if roomInfo.oneself.role == 'MASTER'>
        <div class="columns my-2">
            <div class="column col-7 col-mx-auto">
                <div class="columns">
                    <div class="column col-1 p-0">
                        <span class="ec ec-speaking-head ec_f_md"></span>
                    </div>
                    <div class="column col-5 p-0">
                        <input class="form-input is-success" type="text" id="input-example-1" placeholder="...">
                    </div>
                    <div class="column">
                        <button class="btn btn-info bg-gray">Send Message</button>
                    </div>
                </div>
            </div>
            <#include "./poker_master_bar.ftl">
        </div>
        <div class="divider"></div>
    <#else>
        <#include "./poker_vote_bar.ftl">
        <div class="divider"></div>
    </#if>

    <#if roomInfo.pokers?size gt 0>
        <div class="columns my-2">
            <div class="column col-10 col-mx-auto col-sm-12 col-md-11 col-lg-11 my-2">
                <div class="columns">
                    <#list roomInfo.pokers as poker>
                        <@pokerCard poker />
                    </#list>
                </div>
            </div>
        </div>
    <#else >
        <#include "./empty_room.ftl">
    </#if>
</div>
</body>
</html>

<#macro pokerCard poker>
    <div class="column col-2 col-sm-6 col-md-4 col-lg-3 poker_${poker.id}">
        <div class="card m-1">
            <div class="card-header">
                <div class="card-title">${poker.name?capitalize}</div>
                <#-- <div class="card-subtitle text-gray">${poker.role}</div>-->
            </div>
            <div class="card-body text-center" style="font-size: 65px;">
                <span class="ec ec-zzz"></span>
            </div>
<#--            <div class="card-footer">-->
<#--                <button class="btn btn-link float-right">-->
<#--                    <span class="ec ec-alarm-clock ec_f_md"></span>-->
<#--                </button>-->
<#--            </div>-->
        </div>
    </div>
</#macro>