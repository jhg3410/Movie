from pytube import YouTube

def getVideoStreamUrl(videoId):
    # YouTube URL
    youtube_url = "https://www.youtube.com/watch?v=" + videoId

    # Create a YouTube object
    youtube = YouTube(youtube_url)

    # Get the best stream (video) available
    video_stream = youtube.streams.get_highest_resolution()

    # Get the video URL
    return video_stream.url