import re
import mock
from pytube import YouTube
from pytube.cipher import get_throttling_function_code

def getVideoStreamUrl(videoId):
    with mock.patch('pytube.cipher.get_throttling_plan', patched_throttling_plan):
        # YouTube URL
        youtube_url = "https://www.youtube.com/watch?v=" + videoId

        # Create a YouTube object
        youtube = YouTube(youtube_url)

        # Get the best stream (video) available
        video_stream = youtube.streams.get_highest_resolution()

        # Get the video URL
        return video_stream.url


def patched_throttling_plan(js: str):
    """Patch throttling plan, from https://github.com/pytube/pytube/issues/1498"""
    raw_code = get_throttling_function_code(js)

    transform_start = r"try{"
    plan_regex = re.compile(transform_start)
    match = plan_regex.search(raw_code)

    #transform_plan_raw = find_object_from_startpoint(raw_code, match.span()[1] - 1)
    transform_plan_raw = js

    # Steps are either c[x](c[y]) or c[x](c[y],c[z])
    step_start = r"c\[(\d+)\]\(c\[(\d+)\](,c(\[(\d+)\]))?\)"
    step_regex = re.compile(step_start)
    matches = step_regex.findall(transform_plan_raw)
    transform_steps = []
    for match in matches:
        if match[4] != '':
            transform_steps.append((match[0],match[1],match[4]))
        else:
            transform_steps.append((match[0],match[1]))

    return transform_steps

