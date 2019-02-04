# hello-figwheel

FIXME: Write a one-line description of your library/project.

## Overview

FIXME: Write a paragraph about the library/project and highlight its goals.

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

<svg width="250" height="400">
    <defs>
        <filter id="blur">
            <feGaussianBlur stdDeviation="3"/>
        </filter>
        <radialGradient id="glass-ball-grad1" r="0.4"
                        cx="0.5" cy="0.21"
                        fx="0.5" fy="0.09">
            <stop offset="0" stop-color="#FFFFFFFF"/>
            <stop offset="0.1" stop-color="#FFFFFFFF"/>
            <stop offset="1" stop-color="#FFFFFF00"/>
        </radialGradient>
        <radialGradient id="glass-ball-base-grad" r="1"
                        gradientTransform="scale(1 0.6)"
                        fx="0.5" fy="1.4"
                        cx="0.5" cy="1.45">
            <stop offset="0" stop-color="white"/>
            <stop offset="0.035" stop-color="white"/>
            <stop offset="1" stop-color="purple"/>
        </radialGradient>
        <linearGradient id="ball-linear-grad" x1="0" y1="-1" x2="0" y2="1.05">
            <stop offset="0" stop-color="#FFFFFFFF"/>
            <stop offset="1" stop-color="#FFFFFF00"/>
        </linearGradient>
        <g id="glass-ball" >
            <ellipse filter="url(#blur)"  class="bounce-shadow"  rx="80" ry="30" cy="60" cx="0"/>
            <g class="bounce">
                <g transform="scale(-1 1) rotate(20)" opacity="1">
                    <circle r="80" fill="url(#glass-ball-base-grad)"/>
                    <use href="#refl1" transform="translate(0 37) scale(1.54 1.6)"/>
                    <ellipse id="refl1" rx="40" ry="30" fill="url(#ball-linear-grad)" transform="translate(0 -40)"/>
                    <circle r="80" fill="url(#glass-ball-grad1)"/>
                </g>
            </g>
        </g>
    </defs>
    <use href="#glass-ball" transform="translate(100 300)"/>
</svg>

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
